package ru.citros.documentflow.repository_util.preparedstatementsetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import ru.citros.documentflow.service.converter.Converter;

import java.util.List;
import java.util.UUID;

/**
 * Основной сеттер для полей объектов
 *
 * @param <T> тип
 * @author AIshmaev
 */
public abstract class AbstractBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

    /**
     * Список объектов
     */
    protected List<T> objects;

    @Autowired
    protected Converter converter;

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
