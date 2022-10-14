package ru.citros.documentflow.service.json.deserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.exception.DocumentFlowException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Сериализует объекты
 *
 * @param <T> тип обектов
 * @author AIshmaev
 */
@Service
public class JsonDeserializerImpl<T> implements JsonDeserializer<T> {

    /**
     * Json objectMapper
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> deserialize(String string) {
        if (StringUtils.isBlank(string)) {
            return new ArrayList<>();
        }
        try {
            Map map = objectMapper.readValue(string, Map.class);
            return mapToListValues(map);
        } catch (JsonProcessingException e) {
            throw new DocumentFlowException("Невозможно десериализовать объект. Проверьте файл или объекты!", e);
        }
    }

    /**
     * Конвертирует значения карты в список
     *
     * @param map карта
     * @return список
     */
    private List<T> mapToListValues(Map<T, T> map) {
        List<T> list = new ArrayList<>();
        for (T value : map.values()) {
            list.add(value);
        }
        return list;
    }
}
