package ru.citros.documentflow.repository.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.OutgoingDocument;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.sql.SqlLargeQueryConstants;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Дочерний репозиторий
 *
 * @author AIshmaev
 */
@Repository
public class OutgoingDocumentRepository extends ChildDocumentRepository {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO OUTGOING_DOCUMENT VALUES (?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY = "UPDATE OUTGOING_DOCUMENT SET SENDER_ID=?, DELIVERY_METHOD=? WHERE DOCUMENT_ID=?";

    /**
     * Хранит запрос на удаление записи из таблицы по id
     */
    private static final String DELETE_QUERY = "DELETE FROM OUTGOING_DOCUMENT WHERE DOCUMENT_ID=?";

    @Autowired
    private AbstractBatchPreparedStatementSetter<OutgoingDocument> documentBatchPreparedStatementSetter;

    /**
     * {@inheritDoc}
     */
    @Override
    Document save(Document document) {
        OutgoingDocument outgoingDocument = (OutgoingDocument) document;
        if (outgoingDocument.getSender() == null || outgoingDocument.getSender().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен отправитель");
        }
        jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(outgoingDocument.getId()),
                converter.convertUuidToString(outgoingDocument.getSender().getId()), outgoingDocument.getDeliveryMethod().getName());
        return outgoingDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void saveAll(List<Document> documents) {
        List<OutgoingDocument> outgoingDocuments = documents.stream().map(document -> (OutgoingDocument) document).collect(Collectors.toList());
        documentBatchPreparedStatementSetter.setObjects(outgoingDocuments);
        jdbcTemplate.batchUpdate(INSERT_QUERY, documentBatchPreparedStatementSetter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document update(Document document) {
        OutgoingDocument outgoingDocument = (OutgoingDocument) document;
        if (outgoingDocument.getSender() == null || outgoingDocument.getSender().getId() == null) {
            throw new DocumentFlowException("У документа не заполнен отправитель");
        }
        jdbcTemplate.update(UPDATE_QUERY, converter.convertUuidToString(outgoingDocument.getSender().getId()),
                outgoingDocument.getDeliveryMethod().getName(), converter.convertUuidToString(outgoingDocument.getId()));
        return outgoingDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Document getById(UUID uuid) {
        return jdbcTemplate.queryForObject(SqlLargeQueryConstants.SELECT_ALL_QUERY_FROM_OUTGOING_DOCUMENT,
                documentRowMapper, converter.convertUuidToString(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String getName() {
        return DocumentType.OUTGOING_DOCUMENT.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void delete(Document document) {
        jdbcTemplate.update(DELETE_QUERY, converter.convertUuidToString(document.getId()));
    }
}
