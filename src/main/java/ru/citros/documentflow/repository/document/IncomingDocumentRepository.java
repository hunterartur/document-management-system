package ru.citros.documentflow.repository.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.IncomingDocument;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.sql.SqlLargeQueryConstants;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Дочерний репозиторий
 *
 * @author AIshmaev
 */
@Repository
public class IncomingDocumentRepository extends ChildDocumentRepository {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO INCOMING_DOCUMENT VALUES (?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY =
            "UPDATE INCOMING_DOCUMENT SET SENDER_ID=?, RECIPIENT_ID=?, OUTGOING_NUMBER=?, OUTGOING_REGISTRATION_DATE=? WHERE DOCUMENT_ID=?";

    /**
     * Хранит запрос на удаление записи из таблицы по id
     */
    private static final String DELETE_QUERY = "DELETE FROM INCOMING_DOCUMENT WHERE DOCUMENT_ID=?";

    @Autowired
    private AbstractBatchPreparedStatementSetter<IncomingDocument> documentBatchPreparedStatementSetter;

    /**
     * {@inheritDoc}
     */
    @Override
    Document save(Document document) {
        IncomingDocument incomingDocument = (IncomingDocument) document;
        validateIncomingDocument(incomingDocument);
        Date registrationDate = converter.convertLocalDateToDate(incomingDocument.getRegistrationDate());
        jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(incomingDocument.getId()), converter.convertUuidToString(incomingDocument.getSender().getId()),
                converter.convertUuidToString(incomingDocument.getRecipient().getId()), incomingDocument.getOutgoingNumber(), registrationDate);
        return incomingDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void saveAll(List<Document> documents) {
        List<IncomingDocument> incomingDocuments = documents
                .stream()
                .map(document -> (IncomingDocument) document)
                .collect(Collectors.toList());
        documentBatchPreparedStatementSetter.setObjects(incomingDocuments);
        jdbcTemplate.batchUpdate(INSERT_QUERY, documentBatchPreparedStatementSetter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document update(Document document) {
        IncomingDocument incomingDocument = (IncomingDocument) document;
        validateIncomingDocument(incomingDocument);
        Date registrationDate = converter.convertLocalDateToDate(incomingDocument.getRegistrationDate());
        jdbcTemplate.update(UPDATE_QUERY, converter.convertUuidToString(incomingDocument.getSender().getId()),
                converter.convertUuidToString(incomingDocument.getRecipient().getId()), incomingDocument.getOutgoingNumber(),
                registrationDate, converter.convertUuidToString(incomingDocument.getId()));
        return incomingDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document getById(UUID uuid) {
        return jdbcTemplate.queryForObject(SqlLargeQueryConstants.SELECT_ALL_QUERY_FROM_INCOMING_DOCUMENT,
                documentRowMapper, converter.convertUuidToString(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String getName() {
        return DocumentType.INCOMING_DOCUMENT.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Document document) {
        jdbcTemplate.update(DELETE_QUERY, converter.convertUuidToString(document.getId()));
    }

    /**
     * Проверяет документ на отсутствие null ссылок
     *
     * @param incomingDocument документ
     */
    private void validateIncomingDocument(IncomingDocument incomingDocument) {
        if (incomingDocument.getSender() == null || incomingDocument.getSender().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен отправитель");
        }
        if (incomingDocument.getRecipient() == null || incomingDocument.getRecipient().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен получатель");
        }
    }
}
