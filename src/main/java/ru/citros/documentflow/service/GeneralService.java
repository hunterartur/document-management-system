package ru.citros.documentflow.service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с репозиторием
 *
 * @param <T> тип
 * @author AIshmaev
 */
public interface GeneralService<T> {

    /**
     * Сохраняет сущность
     *
     * @param entity сущность
     * @return сущность
     */
    T save(T entity);

    /**
     * Сохраняет список сущностей
     *
     * @param entities сущности
     * @return список
     */
    List<T> saveAll(List<T> entities);

    /**
     * Обновляет сущность
     *
     * @param entity сущность
     */
    T update(T entity);
}
