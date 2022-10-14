package ru.citros.documentflow.service.json.serialization;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Сериализует объекты
 *
 * @param <T> тип обектов
 * @author AIshmaev
 */
public interface JsonSerializer<T> {

    /**
     * Сериализует объекты в json файл
     *
     * @param file    файл куда будут записаны объекты
     * @param objects объекты которые будут сериализованы
     */
    void serialize(File file, Map.Entry<String, List<T>> objects);

    /**
     * Сериализует объекты в строку
     *
     * @param objects объекты
     * @return строка
     */
    String serialize(List<T> objects);
}
