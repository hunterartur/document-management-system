package ru.citros.documentflow.repository_util.preparedstatementsetter.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.document.TaskDocument;
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
public class TaskDocumentBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<TaskDocument> {

    public TaskDocumentBatchPreparedStatementSetter(List<TaskDocument> documents) {
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
        TaskDocument taskDocument = objects.get(index);
        Person executor = Objects.requireNonNull(taskDocument.getExecutor(),
                MessageFormat.format("В документе №{0} не заполнен исполнитель", taskDocument.getRegistrationNumber()));
        Person controller = Objects.requireNonNull(taskDocument.getController(),
                MessageFormat.format("В документе №{0} не заполнен контроллер", taskDocument.getRegistrationNumber()));
        UUID idExecutor = Objects.requireNonNull(executor.getId(),
                MessageFormat.format("В документе №{0} не заполнен id исполнителя", taskDocument.getRegistrationNumber()));
        UUID idController = Objects.requireNonNull(controller.getId(),
                MessageFormat.format("В документе №{0} не заполнен id контроллера", taskDocument.getRegistrationNumber()));
        Date dateOfIssue = converter.convertLocalDateToDate(taskDocument.getDateOfIssue());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(taskDocument.getId()));
        preparedStatement.setDate(numberPreparedStatement++, dateOfIssue);
        preparedStatement.setInt(numberPreparedStatement++, taskDocument.getTermOfExecution());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idExecutor));
        preparedStatement.setBoolean(numberPreparedStatement++, taskDocument.isControl());
        preparedStatement.setObject(numberPreparedStatement, converter.convertUuidToString(idController));
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
