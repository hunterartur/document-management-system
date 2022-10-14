package ru.citros.documentflow.repository_util.preparedstatementsetter.document;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.model.document.OutgoingDocument;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;

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
public class OutgoingDocumentBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<OutgoingDocument> {

    public OutgoingDocumentBatchPreparedStatementSetter(List<OutgoingDocument> documents) {
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
        OutgoingDocument outgoingDocument = objects.get(index);
        Person sender = Objects.requireNonNull(outgoingDocument.getSender(),
                MessageFormat.format("В документе №{0} не заполнен отправитель", outgoingDocument.getRegistrationNumber()));
        UUID idSender = Objects.requireNonNull(sender.getId(),
                MessageFormat.format("В документе №{0} не заполнен id отправителя", outgoingDocument.getRegistrationNumber()));
        DeliveryMethod notNullDeliveryMethod = Objects.requireNonNull(outgoingDocument.getDeliveryMethod(), "Не указан метод доставки");
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(outgoingDocument.getId()));
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idSender));
        preparedStatement.setString(numberPreparedStatement, notNullDeliveryMethod.getName());
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
