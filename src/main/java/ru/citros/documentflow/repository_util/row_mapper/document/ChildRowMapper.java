package ru.citros.documentflow.repository_util.row_mapper.document;

import org.springframework.jdbc.core.RowMapper;
import ru.citros.documentflow.model.document.Document;

/**
 * Связывает строки таблицы БД и объекта
 *
 * @author AIshmaev
 */
public interface ChildRowMapper extends RowMapper<Document> {

    /**
     * Возвращает тип/имя документа
     *
     * @return тип/имя документа
     */
    String getName();
}
