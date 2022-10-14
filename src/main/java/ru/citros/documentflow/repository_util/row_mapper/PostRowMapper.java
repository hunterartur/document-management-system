package ru.citros.documentflow.repository_util.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Связывает поля записи из БД с объектом
 *
 * @author AIshmaev
 */
@Component
public class PostRowMapper implements RowMapper<Post> {

    /**
     * Маппинг строк из таблицы с объектом
     *
     * @param resultSet результирующий набор для сопоставления (предварительно инициализированный для текущей строки)
     * @param rowNumber номер текущей строки
     * @return объект типа Post
     * @throws SQLException
     */
    @Override
    public Post mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Post post = new Post();
        post.setId(UUID.fromString(resultSet.getString("id")));
        post.setName(resultSet.getString("name"));
        return post;
    }
}
