package ru.citros.documentflow.repository.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.AbstractRowMapper;
import ru.citros.documentflow.service.converter.Converter;
import ru.citros.documentflow.service.json.serialization.JsonSerializer;

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
public class DepartmentRepository implements GeneralRepository<Department> {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO DEPARTMENT VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY =
            "UPDATE DEPARTMENT SET FULL_NAME=?, SHORT_NAME=?, ORGANIZATION_ID=?, DIRECTOR_ID=?, CONTACT_NUMBER=? WHERE ID=?";

    /**
     * Хранит запрос на выборку всех данных из таблицы
     */
    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM DEPARTMENT";

    /**
     * Хранит запрос на выборку данных из таблицы по id
     */
    private static final String SELECT_BY_ID_QUERY =
            "SELECT DEPARTMENT.ID, DEPARTMENT.FULL_NAME, DEPARTMENT.SHORT_NAME, DEPARTMENT.CONTACT_NUMBER, DEPARTMENT.DIRECTOR_ID, DEPARTMENT.ORGANIZATION_ID, " +
                    "O.FULL_NAME as organization_full_name, O.SHORT_NAME as organization_short_name, O.CONTACT_NUMBER as organization_contact_number, " +
                    "P.FIRST_NAME, P.LAST_NAME, P.PATRONYMIC, PHOTO, P.BIRTHDAY, P.PHONE_NUMBER " +
                    "FROM DEPARTMENT " +
                    "JOIN ORGANIZATION O on DEPARTMENT.ORGANIZATION_ID = O.ID " +
                    "JOIN PERSON P on DEPARTMENT.DIRECTOR_ID = P.ID " +
                    "WHERE DEPARTMENT.ID=?";

    /**
     * Хранит запрос на удалении записи из таблицы
     */
    private static final String DELETE_QUERY = "DELETE FROM DEPARTMENT WHERE ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JsonSerializer<String> jsonSerializer;

    @Autowired
    private Converter converter;

    @Autowired
    private AbstractBatchPreparedStatementSetter<Department> departmentBatchPreparedStatementSetter;

    @Autowired
    private AbstractRowMapper<Department> departmentRowMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Department save(Department department) {
        try {
            Department notNullDepartment = Objects.requireNonNull(department, "Отдел равен null");
            UUID idDepartment = (notNullDepartment.getId() != null) ? notNullDepartment.getId() : UUID.randomUUID();
            department.setId(idDepartment);
            UUID idOrganization = (notNullDepartment.getOrganization() != null) ? notNullDepartment.getOrganization().getId() : null;
            UUID idDirector = (notNullDepartment.getDirector() != null) ? notNullDepartment.getDirector().getId() : null;
            jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(idDepartment), notNullDepartment.getFullName(), notNullDepartment.getShortName(),
                    converter.convertUuidToString(idOrganization), jsonSerializer.serialize(notNullDepartment.getContactPhoneNumbers()), converter.convertUuidToString(idDirector));
            return notNullDepartment;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Department> saveAll(List<Department> departments) {
        try {
            departmentBatchPreparedStatementSetter.setObjects(departments);
            jdbcTemplate.batchUpdate(INSERT_QUERY, departmentBatchPreparedStatementSetter);
            return departments;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Department update(Department department) {
        try {
            Department notNullDepartment = Objects.requireNonNull(department, "Отдел равен null");
            UUID idDepartment = Objects.requireNonNull(notNullDepartment.getId(), "id организации равен null");
            UUID idOrganization = (notNullDepartment.getOrganization() != null) ? notNullDepartment.getOrganization().getId() : null;
            UUID idDirector = (notNullDepartment.getDirector() != null) ? notNullDepartment.getDirector().getId() : null;
            int updateRowCount = jdbcTemplate.update(UPDATE_QUERY, notNullDepartment.getFullName(), notNullDepartment.getShortName(), converter.convertUuidToString(idOrganization),
                    converter.convertUuidToString(idDirector), jsonSerializer.serialize(notNullDepartment.getContactPhoneNumbers()), converter.convertUuidToString(notNullDepartment.getId()));
            if (updateRowCount == 0) {
                throw new DocumentFlowException(MessageFormat.format("В бд нет отдела с id={0} для обновления", idDepartment));
            }
            return notNullDepartment;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Department> getAll() {
        try {
            departmentRowMapper.setLazyFetch(true);
            return jdbcTemplate.query(SELECT_ALL_QUERY, departmentRowMapper);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Department getById(UUID uuid) {
        try {
            departmentRowMapper.setLazyFetch(false);
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, departmentRowMapper, converter.convertUuidToString(uuid));
        } catch (EmptyResultDataAccessException ex) {
            throw new DocumentFlowException(MessageFormat.format("Отдела с id={0} не существует!", uuid), ex);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Department department) {
        try {
            jdbcTemplate.update(DELETE_QUERY, converter.convertUuidToString(department.getId()));
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }
}
