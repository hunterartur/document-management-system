package ru.citros.documentflow.repository.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.TaskDocument;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;

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
public class TaskDocumentRepository extends ChildDocumentRepository {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO TASK_DOCUMENT VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY =
            "UPDATE TASK_DOCUMENT SET DATE_OF_ISSUE=?, TERM_OF_EXECUTION=?, EXECUTOR=?, CONTROL=?, CONTROLLER=? WHERE DOCUMENT_ID=?";

    /**
     * Хранит запрос на удаление записи из таблицы по id
     */
    private static final String DELETE_QUERY = "DELETE FROM TASK_DOCUMENT WHERE DOCUMENT_ID=?";

    /**
     * Хранит запрос на выборку документа по id
     */
    private static final String SELECT_BY_ID = "SELECT * FROM TASK_DOCUMENT JOIN DOCUMENT D on TASK_DOCUMENT.DOCUMENT_ID = D.ID WHERE DOCUMENT_ID=?";

    @Autowired
    private AbstractBatchPreparedStatementSetter<TaskDocument> documentBatchPreparedStatementSetter;

    /**
     * {@inheritDoc}
     */
    @Override
    Document save(Document document) {
        TaskDocument taskDocument = (TaskDocument) document;
        validateTaskDocument(taskDocument);
        Date dateOfIssue = converter.convertLocalDateToDate(taskDocument.getDateOfIssue());
        jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(taskDocument.getId()), dateOfIssue,
                taskDocument.getTermOfExecution(), converter.convertUuidToString(taskDocument.getExecutor().getId()),
                taskDocument.isControl(), converter.convertUuidToString(taskDocument.getController().getId()));
        return taskDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void saveAll(List<Document> documents) {
        List<TaskDocument> taskDocuments = documents.stream().map(document -> (TaskDocument) document).collect(Collectors.toList());
        documentBatchPreparedStatementSetter.setObjects(taskDocuments);
        jdbcTemplate.batchUpdate(INSERT_QUERY, documentBatchPreparedStatementSetter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document update(Document document) {
        TaskDocument taskDocument = (TaskDocument) document;
        validateTaskDocument(taskDocument);
        Date dateOfIssue = converter.convertLocalDateToDate(taskDocument.getDateOfIssue());
        jdbcTemplate.update(UPDATE_QUERY, dateOfIssue, taskDocument.getTermOfExecution(),
                converter.convertUuidToString(taskDocument.getExecutor().getId()), taskDocument.isControl(),
                converter.convertUuidToString(taskDocument.getController().getId()), converter.convertUuidToString(taskDocument.getId()));
        return taskDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document getById(UUID uuid) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, documentRowMapper, converter.convertUuidToString(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String getName() {
        return DocumentType.TASK_DOCUMENT.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Document document) {
        jdbcTemplate.update(DELETE_QUERY, converter.convertUuidToString(document.getId()));
    }

    private void validateTaskDocument(TaskDocument taskDocument) {
        if (taskDocument.getExecutor() == null || taskDocument.getExecutor().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен исполнитель");
        }
        if (taskDocument.getController() == null || taskDocument.getController().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен контроллер");
        }
    }
}
