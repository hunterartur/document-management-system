package ru.citros.documentflow.repository_util.preparedstatementsetter.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.document.IncomingDocument;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.service.converter.Converter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Заполняет параметры sql выражения
 *
 * @author AIshmaev
 */
@Component
public class IncomingDocumentBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<IncomingDocument> {

    public IncomingDocumentBatchPreparedStatementSetter(List<IncomingDocument> documents) {
        this.objects = documents;
    }

    /**
     * Подставляет значение в параметры запроса
     *
     * @param preparedStatement подготовленный оператор для вызова методов setter
     * @param index             индекс инструкции, которую мы выдаем в пакете, начиная с 0
     * @throws SQLException
     */
    @Override
    public void setValues(PreparedStatement preparedStatement, int index) throws SQLException {
        int numberPreparedStatement = 1;
        IncomingDocument incomingDocument = objects.get(index);
        Person sender = Objects.requireNonNull(incomingDocument.getSender(),
                MessageFormat.format("В документе №{0} не заполнен отправитель", incomingDocument.getRegistrationNumber()));
        Person recipient = Objects.requireNonNull(incomingDocument.getRecipient(),
                MessageFormat.format("В документе №{0} не заполнен получатель", incomingDocument.getRegistrationNumber()));
        UUID idSender = Objects.requireNonNull(sender.getId(),
                MessageFormat.format("В документе №{0} не заполнен id отправителя", incomingDocument.getRegistrationNumber()));
        UUID idRecipient = Objects.requireNonNull(recipient.getId(),
                MessageFormat.format("В документе №{0} не заполнен id получателя", incomingDocument.getRegistrationNumber()));
        Date outgoingRegistrationDate = converter.convertLocalDateToDate(incomingDocument.getOutgoingRegistrationDate());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(incomingDocument.getId()));
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idSender));
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idRecipient));
        preparedStatement.setString(numberPreparedStatement++, incomingDocument.getOutgoingNumber());
        preparedStatement.setDate(numberPreparedStatement, outgoingRegistrationDate);
    }

    /**
     * Возвращает размер списка
     *
     * @return размер списка
     */
    @Override
    public int getBatchSize() {
        return objects.size();
    }
}
