package ru.citros.documentflow.repository.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.PersonBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.AbstractRowMapper;
import ru.citros.documentflow.service.converter.Converter;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для CRUD операции
 *
 * @author AIshmaev
 */
@Repository
public class PersonRepository implements GeneralRepository<Person> {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO PERSON VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY =
            "UPDATE PERSON SET FIRST_NAME=?, LAST_NAME=?, PATRONYMIC=?, PHOTO=?, BIRTHDAY=?, PHONE_NUMBER=?, POST_ID=?, DEPARTMENT_ID=? WHERE ID=?";

    /**
     * Хранит запрос на выборку всех данных из таблицы
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM PERSON";

    /**
     * Хранит запрос на выборку данных из таблицы по id
     */
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM PERSON, POST, DEPARTMENT WHERE POST_ID=POST.ID AND DEPARTMENT_ID=DEPARTMENT.ID AND PERSON.ID=?";

    /**
     * Хранит запрос на удалении записи из таблицы
     */
    private static final String DELETE_QUERY = "DELETE FROM PERSON WHERE ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonBatchPreparedStatementSetter personBatchPreparedStatementSetter;

    @Autowired
    private Converter converter;

    @Autowired
    private AbstractRowMapper<Person> personRowMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Person save(Person person) {
        try {
            Person notNullPerson = Objects.requireNonNull(person, "Сотрудник равен null");
            UUID idPerson = (notNullPerson.getId() != null) ? notNullPerson.getId() : UUID.randomUUID();
            person.setId(idPerson);
            UUID idPost = (notNullPerson.getPost() != null) ? notNullPerson.getPost().getId() : null;
            UUID idDepartment = (notNullPerson.getDepartment() != null) ? notNullPerson.getDepartment().getId() : null;
            Date birthday = converter.convertLocalDateToDate(notNullPerson.getBirthday());
            jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(idPerson), notNullPerson.getFirstName(), notNullPerson.getLastName(), notNullPerson.getPatronymic(),
                    notNullPerson.getPhoto(), birthday, notNullPerson.getPhoneNumber(), converter.convertUuidToString(idPost), converter.convertUuidToString(idDepartment));
            return notNullPerson;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> saveAll(List<Person> people) {
        try {
            personBatchPreparedStatementSetter.setObjects(people);
            jdbcTemplate.batchUpdate(INSERT_QUERY, personBatchPreparedStatementSetter);
            return people;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person update(Person person) {
        try {
            Person notNullPerson = Objects.requireNonNull(person, "Сотрудник равен null");
            UUID idPerson = Objects.requireNonNull(notNullPerson.getId(), "id сотрудника равен null");
            UUID idPost = (notNullPerson.getPost() != null) ? notNullPerson.getPost().getId() : null;
            UUID idDepartment = (notNullPerson.getDepartment() != null) ? notNullPerson.getDepartment().getId() : null;
            Date birthday = converter.convertLocalDateToDate(notNullPerson.getBirthday());
            int updateRowCount = jdbcTemplate.update(UPDATE_QUERY, notNullPerson.getFirstName(), notNullPerson.getLastName(), notNullPerson.getPatronymic(), notNullPerson.getPhoto(),
                    birthday, notNullPerson.getPhoneNumber(), converter.convertUuidToString(idPost),
                    converter.convertUuidToString(idDepartment), converter.convertUuidToString(notNullPerson.getId()));
            if (updateRowCount == 0) {
                throw new DocumentFlowException(MessageFormat.format("В бд нет сотрудника с id={0} для обновления", idPerson));
            }
            return notNullPerson;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getAll() {
        try {
            personRowMapper.setLazyFetch(true);
            return jdbcTemplate.query(SELECT_ALL_QUERY, personRowMapper);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person getById(UUID uuid) {
        try {
            personRowMapper.setLazyFetch(false);
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, personRowMapper, converter.convertUuidToString(uuid));
        } catch (EmptyResultDataAccessException ex) {
            throw new DocumentFlowException(MessageFormat.format("Сотрудника с id={0} не существует!", uuid.toString()), ex);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Person person) {
        try {
            jdbcTemplate.update(DELETE_QUERY, converter.convertUuidToString(person.getId()));
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }
}
