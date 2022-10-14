package ru.citros.documentflow.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для CRUD операции
 *
 * @param <T> тип сущности
 * @author AIshmaev
 */
public interface GeneralRepository<T> {

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

    /**
     * Предоставляет все сущности из репозитория
     *
     * @return список сущностей
     */
    List<T> getAll();

    /**
     * Предоставляет сущность из репозитория по uuid
     *
     * @param uuid идентификатор
     * @return объект optional
     */
    T getById(UUID uuid);

    /**
     * Удаляет сущность
     *
     * @param entity сущность
     */
    void delete(T entity);
}
