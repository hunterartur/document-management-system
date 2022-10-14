package ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.service.converter.Converter;

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
public class PersonBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<Person> {

    @Autowired
    private Converter converter;

    public PersonBatchPreparedStatementSetter(List<Person> people) {
        this.objects = people;
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
        Person person = objects.get(index);
        UUID idPerson = (person.getId() != null) ? person.getId() : UUID.randomUUID();
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(idPerson));
        preparedStatement.setString(numberPreparedStatement++, person.getFirstName());
        preparedStatement.setString(numberPreparedStatement++, person.getLastName());
        preparedStatement.setString(numberPreparedStatement++, person.getPatronymic());
        preparedStatement.setString(numberPreparedStatement++, person.getPhoto());
        preparedStatement.setDate(numberPreparedStatement++, converter.convertLocalDateToDate(person.getBirthday()));
        preparedStatement.setString(numberPreparedStatement++, person.getPhoneNumber());
        preparedStatement.setObject(numberPreparedStatement++, converter.convertUuidToString(person.getPost().getId()));
        preparedStatement.setObject(numberPreparedStatement, converter.convertUuidToString(person.getDepartment().getId()));
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
