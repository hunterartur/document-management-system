package ru.citros.documentflow.service.json.deserialization;

import java.util.List;

/**
 * Сериализует объекты
 *
 * @param <T> тип обектов
 * @author AIshmaev
 */
public interface JsonDeserializer<T> {

    /**
     * Сериализует объекты в строку
     *
     * @param string строка
     * @return список
     */
    List<T> deserialize(String string);
}
