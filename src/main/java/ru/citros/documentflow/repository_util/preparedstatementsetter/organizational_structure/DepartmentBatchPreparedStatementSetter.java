package ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.service.json.serialization.JsonSerializer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Заполняет параметры sql выражения
 *
 * @author AIshmaev
 */
@Component
public class DepartmentBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<Department> {

    @Autowired
    private JsonSerializer<String> jsonSerializer;

    public DepartmentBatchPreparedStatementSetter(List<Department> departments) {
        this.objects = departments;
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
        Department department = objects.get(index);
        UUID idDepartment = (department.getId() != null) ? department.getId() : UUID.randomUUID();
        String serializeContactNumbers = jsonSerializer.serialize(department.getContactPhoneNumbers());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idDepartment));
        preparedStatement.setString(numberPreparedStatement++, department.getFullName());
        preparedStatement.setString(numberPreparedStatement++, department.getShortName());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(department.getOrganization().getId()));
        preparedStatement.setString(numberPreparedStatement++, serializeContactNumbers);
        preparedStatement.setObject(numberPreparedStatement, converter.convertUuidToString(department.getDirector().getId()));
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
