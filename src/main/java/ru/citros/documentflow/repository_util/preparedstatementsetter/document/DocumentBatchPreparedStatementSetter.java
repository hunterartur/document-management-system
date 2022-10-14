package ru.citros.documentflow.repository_util.preparedstatementsetter.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.document.Document;
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
public class DocumentBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<Document> {

    public DocumentBatchPreparedStatementSetter(List<Document> documents) {
        this.objects = documents;
    }

    @Autowired
    private Converter converter;

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
        Document document = objects.get(index);
        Person author = Objects.requireNonNull(document.getAuthor(),
                MessageFormat.format("В документе №{0} не заполнен автор", document.getRegistrationNumber()));
        UUID idAuthor = Objects.requireNonNull(author.getId(),
                MessageFormat.format("В документе №{0} не заполнен id автора", document.getRegistrationNumber()));
        UUID idDocument = (document.getId() != null) ? document.getId() : UUID.randomUUID();
        Date registrationDate = converter.convertLocalDateToDate(document.getRegistrationDate());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idDocument));
        preparedStatement.setString(numberPreparedStatement++, document.getName());
        preparedStatement.setString(numberPreparedStatement++, document.getText());
        preparedStatement.setString(numberPreparedStatement++, document.getRegistrationNumber());
        preparedStatement.setString(numberPreparedStatement++, document.getClass().getSimpleName());
        preparedStatement.setDate(numberPreparedStatement++, registrationDate);
        preparedStatement.setObject(numberPreparedStatement, converter.convertUuidToString(idAuthor));
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
