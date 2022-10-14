package ru.citros.documentflow.repository_util.preparedstatementsetter;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.model.organizational_structure.Post;

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
public class PostBatchPreparedStatementSetter extends AbstractBatchPreparedStatementSetter<Post> {

    public PostBatchPreparedStatementSetter(List<Post> posts) {
        this.objects = posts;
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
        Post post = objects.get(index);
        UUID idPost = (post.getId() != null) ? post.getId() : UUID.randomUUID();
        preparedStatement.setObject(numberPreparedStatement++, idPost);
        preparedStatement.setString(numberPreparedStatement, post.getName());
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
