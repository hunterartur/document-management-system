package ru.citros.documentflow.repository_util.row_mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import ru.citros.documentflow.service.converter.Converter;
import ru.citros.documentflow.service.json.deserialization.JsonDeserializer;

/**
 * Добавляет поля и методы к классам реализующих RowMapper
 *
 * @param <T> тип
 * @author AIshmaev
 */
public abstract class AbstractRowMapper<T> implements RowMapper<T> {

    /**
     * Флаг для установки условия связывания
     */
    protected boolean lazyFetch;

    @Autowired
    protected JsonDeserializer<String> stringJsonDeserializer;

    @Autowired
    protected Converter converter;

    public void setLazyFetch(boolean lazyFetch) {
        this.lazyFetch = lazyFetch;
    }
}
