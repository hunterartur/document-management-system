package ru.citros.documentflow.repository_util.row_mapper.organizational_structure;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository_util.row_mapper.AbstractRowMapper;

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
public class PersonRowMapper extends AbstractRowMapper<Person> {

    /**
     * Маппинг строк из таблицы с объектом
     *
     * @param resultSet результирующий набор для сопоставления (предварительно инициализированный для текущей строки)
     * @param rowNumber номер текущей строки
     * @return объект типа Person
     * @throws SQLException
     */
    @Override
    public Person mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        UUID departmentId = converter.convertStringIdToUuid(resultSet.getString("department_id"));
        Department department = Department.newBuilder()
                .setId(departmentId)
                .build();
        UUID postID = converter.convertStringIdToUuid(resultSet.getString("post_id"));
        Post post = new Post();
        post.setId(postID);
        if (!lazyFetch) {
            List<String> departmentContactNumber = stringJsonDeserializer.deserialize(resultSet.getString("contact_number"));
            department.setContactPhoneNumbers(departmentContactNumber);
            department.setFullName(resultSet.getString("full_name"));
            department.setShortName(resultSet.getString("short_name"));
            String postName = resultSet.getString("name");
            post.setName(postName);
        }
        return Person.newBuilder()
                .setId(UUID.fromString(resultSet.getString("id")))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPatronymic(resultSet.getString("patronymic"))
                .setDepartment(department)
                .setPost(post)
                .setPhoto(resultSet.getString("photo"))
                .setBirthday(resultSet.getDate("birthday").toLocalDate())
                .setPhoneNumber(resultSet.getString("phone_number"))
                .build();
    }
}
