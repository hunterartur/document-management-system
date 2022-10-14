package ru.citros.documentflow.repository_util.row_mapper.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.TaskDocument;
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
public class TaskDocumentRowMapper implements ChildRowMapper {

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
        LocalDate dateOfIssue = converter.convertDateToLocalDate(resultSet.getDate("date_of_issue"));
        UUID idExecutor = (resultSet.getString("executor") != null) ?  UUID.fromString(resultSet.getString("executor")) : null;
        Person executor = Person.newBuilder()
                .setId(idExecutor)
                .build();
        UUID idController = (resultSet.getString("controller") != null) ?  UUID.fromString(resultSet.getString("controller")) : null;
        Person controller = Person.newBuilder()
                .setId(idController)
                .build();
        return TaskDocument.newBuilder()
                .setDateIssue(dateOfIssue)
                .setTermOfExecution(resultSet.getInt("term_of_execution"))
                .setControl(resultSet.getBoolean("control"))
                .setExecutor(executor)
                .setController(controller)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.TASK_DOCUMENT.getName();
    }
}
