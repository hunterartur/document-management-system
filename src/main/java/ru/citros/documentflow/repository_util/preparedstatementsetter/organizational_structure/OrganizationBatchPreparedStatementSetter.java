package ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Organization;
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
public class OrganizationBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<Organization> {

    @Autowired
    private JsonSerializer<String> jsonSerializer;

    public OrganizationBatchPreparedStatementSetter(List<Organization> organizations) {
        this.objects = organizations;
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
        Organization organization = objects.get(index);
        UUID idOrganization = (organization.getId() != null) ? organization.getId() : UUID.randomUUID();
        String serializeContactNumbers = jsonSerializer.serialize(organization.getContactPhoneNumbers());
        UUID idDirector = (organization.getDirector() != null) ? organization.getDirector().getId() : null;
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idOrganization));
        preparedStatement.setString(numberPreparedStatement++, organization.getFullName());
        preparedStatement.setString(numberPreparedStatement++, organization.getShortName());
        preparedStatement.setString(numberPreparedStatement++, serializeContactNumbers);
        preparedStatement.setObject(numberPreparedStatement, converter.convertUuidToString(idDirector));
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
