package ru.citros.documentflow.repository.organizational_structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Organization;
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
public class OrganizationRepository implements GeneralRepository<Organization> {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "INSERT INTO ORGANIZATION VALUES (?, ?, ?, ?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY = "UPDATE ORGANIZATION SET FULL_NAME=?, SHORT_NAME=?, DIRECTOR_ID=?, CONTACT_NUMBER=? WHERE ID=?";

    /**
     * Хранит запрос на выборку всех данных из таблицы
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ORGANIZATION";

    /**
     * Хранит запрос на выборку данных из таблицы по id
     */
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM ORGANIZATION JOIN PERSON P on ORGANIZATION.DIRECTOR_ID = P.ID WHERE ORGANIZATION.ID=?";

    /**
     * Хранит запрос на удалении записи из таблицы
     */
    private static final String DELETE_QUERY = "DELETE FROM ORGANIZATION WHERE ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JsonSerializer<String> jsonSerializer;

    @Autowired
    private AbstractBatchPreparedStatementSetter<Organization> organizationBatchPreparedStatementSetter;

    @Autowired
    private AbstractRowMapper<Organization> organizationGeneralRowMapper;

    @Autowired
    private Converter converter;

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization save(Organization organization) {
        try {
            Organization notNullOrganization = Objects.requireNonNull(organization, "Организация равна null");
            UUID idOrganization = (notNullOrganization.getId() != null) ? notNullOrganization.getId() : UUID.randomUUID();
            organization.setId(idOrganization);
            UUID idDirector = (notNullOrganization.getDirector() != null) ? notNullOrganization.getDirector().getId() : null;
            String contactNumbersJson = jsonSerializer.serialize(notNullOrganization.getContactPhoneNumbers());
            jdbcTemplate.update(INSERT_QUERY, converter.convertUuidToString(idOrganization), notNullOrganization.getFullName(),
                    notNullOrganization.getShortName(), contactNumbersJson, converter.convertUuidToString(idDirector));
            return notNullOrganization;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> saveAll(List<Organization> organizations) {
        try {
            organizationBatchPreparedStatementSetter.setObjects(organizations);
            jdbcTemplate.batchUpdate(INSERT_QUERY, organizationBatchPreparedStatementSetter);
            return organizations;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization update(Organization organization) {
        try {
            Organization notNullOrganization = Objects.requireNonNull(organization, "Организация равна null");
            UUID idOrganization = Objects.requireNonNull(notNullOrganization.getId(), "id сотрудника равен null");
            UUID idDirector = (notNullOrganization.getDirector() != null) ? notNullOrganization.getDirector().getId() : null;
            String contactNumbersJson = jsonSerializer.serialize(notNullOrganization.getContactPhoneNumbers());
            int updateRowCount = jdbcTemplate.update(UPDATE_QUERY, notNullOrganization.getFullName(),
                    notNullOrganization.getShortName(), converter.convertUuidToString(idDirector), contactNumbersJson, converter.convertUuidToString(notNullOrganization.getId()));
            if (updateRowCount == 0) {
                throw new DocumentFlowException(MessageFormat.format("В бд нет организации с id={0} для обновления", idOrganization));
            }
            return notNullOrganization;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> getAll() {
        try {
            organizationGeneralRowMapper.setLazyFetch(true);
            return jdbcTemplate.query(SELECT_ALL_QUERY, organizationGeneralRowMapper);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization getById(UUID uuid) {
        try {
            organizationGeneralRowMapper.setLazyFetch(false);
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, organizationGeneralRowMapper, uuid.toString());
        } catch (EmptyResultDataAccessException ex) {
            throw new DocumentFlowException(MessageFormat.format("Организация с id={0} не существует!", uuid.toString()), ex);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Organization organization) {
        try {
            jdbcTemplate.update(DELETE_QUERY, organization.getId().toString());
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }
}
