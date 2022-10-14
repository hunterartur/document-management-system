package ru.citros.documentflow.service.validate;

/**
 * Интерфейс для валидации обектов.
 *
 * @author AIshmaev
 */
public interface ValidateService<T> {

    /**
     * Валидирует объекты
     *
     * @param object объект который необходимо проверить
     * @return строка с ошибками
     */
    String validate(T object);
}
