package ru.citros.documentflow.service.json.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.exception.DocumentFlowException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сериализует объекты
 *
 * @param <T> тип обектов
 * @author AIshmaev
 */
@Service
public class JsonSerializerImpl<T> implements JsonSerializer<T> {

    /**
     * записывает объекты в файл в json формате
     */
    private final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(File file, Map.Entry<String, List<T>> objects) {
        try {
            writer.writeValue(file, objects);
        } catch (IOException e) {
            throw new DocumentFlowException("Невозможно сериализовать объект. Проверьте файл или объекты!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize(List<T> objects) {
        try {
            return writer.writeValueAsString(listToMap(objects));
        } catch (JsonProcessingException e) {
            throw new DocumentFlowException("Невозможно сериализовать объект. Проверьте файл или объекты!", e);
        }
    }

    /**
     * Преобразует список в пронумерованную карту
     *
     * @param list список
     * @return пронумерованная карта
     */
    private Map<Integer, String> listToMap(List<T> list) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i + 1, list.get(i).toString());
        }
        return map;
    }
}
