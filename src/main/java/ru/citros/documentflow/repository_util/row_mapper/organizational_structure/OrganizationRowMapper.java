package ru.citros.documentflow.repository_util.row_mapper.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository_util.row_mapper.AbstractRowMapper;
import ru.citros.documentflow.service.json.deserialization.JsonDeserializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Связывает поля записи из БД с объектом
 *
 * @author AIshmaev
 */
@Component
public class OrganizationRowMapper extends AbstractRowMapper<Organization> {

    /**
     * Маппинг строк из таблицы с объектом
     *
     * @param resultSet результирующий набор для сопоставления (предварительно инициализированный для текущей строки)
     * @param rowNumber номер текущей строки
     * @return объект типа Organization
     * @throws SQLException
     */
    @Override
    public Organization mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        UUID directorId = converter.convertStringIdToUuid(resultSet.getString("director_id"));
        Person person = Person.newBuilder()
                .setId(directorId)
                .build();
        if (!lazyFetch) {
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setPatronymic(resultSet.getString("patronymic"));
            person.setPhoto(resultSet.getString("photo"));
            person.setBirthday(resultSet.getDate("birthday").toLocalDate());
            person.setPhoneNumber(resultSet.getString("phone_number"));
        }
        List<String> organizationContactNumber = stringJsonDeserializer.deserialize(resultSet.getString("contact_number"));
        return Organization.newBuilder()
                .setId(UUID.fromString(resultSet.getString("id")))
                .setFullName(resultSet.getString("full_name"))
                .setShortName(resultSet.getString("short_name"))
                .setContactPhoneNumbers(organizationContactNumber)
                .setDirector(person)
                .build();
    }
}
