package ru.citros.documentflow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository_util.preparedstatementsetter.AbstractBatchPreparedStatementSetter;

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
public class PostRepository implements GeneralRepository<Post> {

    /**
     * Хранит запрос на добавление данных в таблицу
     */
    private static final String INSERT_QUERY = "insert into POST values (?, ?)";

    /**
     * Хранит запрос на обновление данных в таблице
     */
    private static final String UPDATE_QUERY = "UPDATE POST SET NAME=? WHERE ID=?";

    /**
     * Хранит запрос на выборку всех данных из таблицы
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM POST";

    /**
     * Хранит запрос на выборку данных из таблицы по id
     */
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM POST WHERE ID=?";

    /**
     * Хранит запрос на удалении записи из таблицы
     */
    private static final String DELETE_QUERY = "DELETE FROM POST WHERE ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AbstractBatchPreparedStatementSetter<Post> postBatchPreparedStatementSetter;

    @Autowired
    private RowMapper<Post> postRowMapper;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Post save(Post post) {
        try {
            Post notNullPost = Objects.requireNonNull(post, "Должность равна null");
            UUID idPost = (notNullPost.getId() != null) ? notNullPost.getId() : UUID.randomUUID();
            jdbcTemplate.update(INSERT_QUERY, idPost.toString(), notNullPost.getName());
            return notNullPost;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Post> saveAll(List<Post> posts) {
        try {
            postBatchPreparedStatementSetter.setObjects(posts);
            jdbcTemplate.batchUpdate(INSERT_QUERY, postBatchPreparedStatementSetter);
            return posts;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Post update(Post post) {
        try {
            Post notNullPost = Objects.requireNonNull(post, "Должность равна null");
            UUID idPost = Objects.requireNonNull(notNullPost.getId(), "id должности равен null");
            int updateRowCount = jdbcTemplate.update(UPDATE_QUERY, notNullPost.getName(), idPost.toString());
            if (updateRowCount == 0) {
                throw new DocumentFlowException(MessageFormat.format("В бд нет должности с id={0} для обновления", idPost));
            }
            return notNullPost;
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Post> getAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL_QUERY, postRowMapper);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Post getById(UUID uuid) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, postRowMapper, uuid.toString());
        } catch (EmptyResultDataAccessException ex) {
            throw new DocumentFlowException(MessageFormat.format("Должности с id={0} не существует!", uuid.toString()), ex);
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Post post) {
        try {
            jdbcTemplate.update(DELETE_QUERY, post.getId().toString());
        } catch (DataAccessException e) {
            throw new DocumentFlowException("Нет доступа к базе данных! Проверьте подключение к БД!", e);
        }
    }
}
