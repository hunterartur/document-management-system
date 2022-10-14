package ru.citros.documentflow.repository_util.row_mapper.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.IncomingDocument;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.service.converter.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Связывает поля записи из БД с объектом
 *
 * @author AIshmaev
 */
@Component
public class IncomingDocumentRowMapper implements ChildRowMapper {

    @Autowired
    private Converter converter;

    /**
     * Маппинг строк из таблицы с объектом
     *
     * @param resultSet результирующий набор для сопоставления (предварительно инициализированный для текущей строки)
     * @param rowNumber номер текущей строки
     * @return объект типа Document
     * @throws SQLException
     */
    @Override
    public Document mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        LocalDate outgoingRegistrationDate = converter.convertDateToLocalDate(resultSet.getDate("outgoing_registration_date"));
        UUID idSender = (resultSet.getString("incoming_document_sender_id") != null) ? UUID.fromString(resultSet.getString("incoming_document_sender_id")) : null;
        Person sender = Person.newBuilder()
                .setId(idSender)
                .build();
        UUID idRecipient = (resultSet.getString("recipient_id") != null) ? UUID.fromString(resultSet.getString("recipient_id")) : null;
        Person recipient = Person.newBuilder()
                .setId(idRecipient)
                .build();
        return IncomingDocument.newBuilder()
                .setOutgoingNumber(resultSet.getString("outgoing_number"))
                .setOutgoingRegistrationDate(outgoingRegistrationDate)
                .setSender(sender)
                .setRecipient(recipient)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.INCOMING_DOCUMENT.getName();
    }
}
