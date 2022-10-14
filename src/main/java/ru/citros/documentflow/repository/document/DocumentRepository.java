package ru.citros.documentflow.repository.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.service.converter.Converter;
import ru.citros.documentflow.sql.SqlLargeQueryConstants;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Repository
public class DocumentRepository implements GeneralRepository<Document> {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_BASE_DOCUMENT_QUERY = "INSERT INTO DOCUMENT VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY = "UPDATE DOCUMENT SET NAME=?, TEXT=?, REGISTRATION_NUMBER=?, REGISTRATION_DATE=?, AUTHOR=? WHERE ID=?";

    /**
     * Хранит запрос на удаление записи из таблицы по id
     */
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM DOCUMENT WHERE DOCUMENT.ID=?";

    /**
     * Хранит запрос на выборку типа документа по id
     */
    private static final String SELECT_DOCUMENT_TYPE_BY_ID = "SELECT DOCUMENT_TYPE FROM DOCUMENT WHERE ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Document> documentRowMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private List<ChildDocumentRepository> childDocumentRepositories;

    @Autowired
    private Converter converter;

    @Autowired
    private AbstractBatchPreparedStatementSetter<Document> documentBatchPreparedStatementSetter;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Document save(Document document) {
        try {
            Document notNullDocument = Objects.requireNonNull(document, "Документ равен null");
            if (notNullDocument.getAuthor() == null || notNullDocument.getAuthor().getId() == null) {
                throw new DocumentFlowException("У документа не заполнен автор");
            }
            UUID idDocument = (notNullDocument.getId() != null) ? notNullDocument.getId() : UUID.randomUUID();
            document.setId(idDocument);
            Date registrationDate = converter.convertLocalDateToDate(notNullDocument.getRegistrationDate());
            jdbcTemplate.update(INSERT_BASE_DOCUMENT_QUERY, converter.convertUuidToString(idDocument), notNullDocument.getName(),
                    notNullDocument.getText(), notNullDocument.getRegistrationNumber(), notNullDocument.getClass().getSimpleName(),
                    registrationDate, converter.convertUuidToString(notNullDocument.getAuthor().getId()));
            insertChildDocument(notNullDocument);
            return notNullDocument;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Document update(Document document) {
        try {
            Document notNullDocument = Objects.requireNonNull(document, "Документ равен null");
            UUID idDocument = Objects.requireNonNull(notNullDocument.getId(), "id документа равен null");
            if (notNullDocument.getAuthor() == null || notNullDocument.getAuthor().getId() == null) {
                throw new DocumentFlowException("У документа не заполнен автор");
            }
            Date registrationDate = converter.convertLocalDateToDate(document.getRegistrationDate());
            int updateRowCount = jdbcTemplate.update(UPDATE_QUERY, notNullDocument.getName(), notNullDocument.getText(), notNullDocument.getRegistrationNumber(), registrationDate,
                    converter.convertUuidToString(notNullDocument.getAuthor().getId()), converter.convertUuidToString(notNullDocument.getId()));
            if (updateRowCount == 0) {
                throw new DocumentFlowException(MessageFormat.format("В бд нет документа с id={0} для обновления", idDocument));
            }
            updateChildDocument(document);
            return document;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Document> saveAll(List<Document> documents) {
        documentBatchPreparedStatementSetter.setObjects(documents);
        jdbcTemplate.batchUpdate(INSERT_BASE_DOCUMENT_QUERY, documentBatchPreparedStatementSetter);
        Map<String, List<Document>> collect = documents.stream().collect(Collectors.groupingBy(document -> document.getClass().getSimpleName()));
        for (Map.Entry<String, List<Document>> documentsByType : collect.entrySet()) {
            defineChildRepositoryByClassSimpleName(documentsByType.getValue().get(0).getClass().getSimpleName()).saveAll(documentsByType.getValue());
        }
        return documents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Document> getAll() {
        try {
            return jdbcTemplate.query(SqlLargeQueryConstants.SELECT_ALL_QUERY_FROM_BASE_DOCUMENT, documentRowMapper);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document getById(UUID uuid) {
        try {
            String documentType = jdbcTemplate.queryForObject(SELECT_DOCUMENT_TYPE_BY_ID, String.class, converter.convertUuidToString(uuid));
            return defineChildRepositoryByClassSimpleName(documentType).getById(uuid);
        } catch (EmptyResultDataAccessException ex) {
            throw new DocumentFlowException(MessageFormat.format("Документа с id={0} не существует!", uuid), ex);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Document document) {
        try {
            defineChildRepositoryByClassSimpleName(document.getClass().getSimpleName()).delete(document);
            jdbcTemplate.update(DELETE_BY_ID_QUERY, converter.convertUuidToString(document.getId()));
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * Записывает данные в дочернию таблицу
     *
     * @param document документ
     */
    private void insertChildDocument(Document document) {
        try {
            defineChildRepositoryByClassSimpleName(document.getClass().getSimpleName()).save(document);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * Обновляет данные в дочерней таблице
     *
     * @param document документ
     */
    private void updateChildDocument(Document document) {
        try {
            defineChildRepositoryByClassSimpleName(document.getClass().getSimpleName()).update(document);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * Определяет дочерний репозиторий по имени классу или имени типа документа
     *
     * @param classSimpleName имя класса или имя типа документа
     * @return дочерний репозиторий
     */
    private ChildDocumentRepository defineChildRepositoryByClassSimpleName(String classSimpleName) {
        DocumentType documentTypeByClassSimpleName = getDocumentTypeByClassSimpleName(classSimpleName);
        return childDocumentRepositories.stream()
                .filter(childRepository -> documentTypeByClassSimpleName.getName().equals(childRepository.getName()))
                .findAny()
                .orElseThrow(() -> new DocumentFlowException(MessageFormat.format("Репозитория для типа {0} не существует!", documentTypeByClassSimpleName)));
    }

    /**
     * Определяет тип документа по имени класса
     *
     * @param classSimpleName имя класса
     * @return тип документа
     */
    private DocumentType getDocumentTypeByClassSimpleName(String classSimpleName) {
        return Arrays.stream(DocumentType.values())
                .filter(type -> classSimpleName.equals(type.getName()))
                .findAny()
                .get();
    }
}
