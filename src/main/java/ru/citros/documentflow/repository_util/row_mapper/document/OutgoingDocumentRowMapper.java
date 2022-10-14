package ru.citros.documentflow.repository_util.row_mapper.document;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.OutgoingDocument;
import ru.citros.documentflow.model.organizational_structure.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Связывает поля записи из БД с объектом
 *
 * @author AIshmaev
 */
@Component
public class OutgoingDocumentRowMapper implements ChildRowMapper {

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
        String deliveryMethodFromTable = resultSet.getString("delivery_method");
        DeliveryMethod deliveryMethod = getDeliveryMethodFromString(deliveryMethodFromTable);
        UUID idSender = (resultSet.getString("outgoing_document_sender_id") != null) ? UUID.fromString(resultSet.getString("outgoing_document_sender_id")) : null;
        Person sender = Person.newBuilder()
                .setId(idSender)
                .build();
        return OutgoingDocument.newBuilder()
                .setDeliveryMethod(deliveryMethod)
                .setSender(sender)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.OUTGOING_DOCUMENT.getName();
    }

    /**
     * Находит метод доставки по названию
     *
     * @param stringDeliveryMethod название
     * @return метод доставки или null
     */
    private DeliveryMethod getDeliveryMethodFromString(String stringDeliveryMethod) {
        if (stringDeliveryMethod == null || stringDeliveryMethod.isEmpty()) {
            return null;
        }
        return Arrays.stream(DeliveryMethod.values())
                .filter(method -> stringDeliveryMethod.equals(method.getName()))
                .findFirst()
                .orElseThrow(() -> new DocumentFlowException("Нет такого метода доставки!"));
    }
}
