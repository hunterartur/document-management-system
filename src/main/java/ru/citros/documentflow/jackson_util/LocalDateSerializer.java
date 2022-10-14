package ru.citros.documentflow.jackson_util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Предоставляет инструмент для сериализации LocalDate в Date и обратно
 *
 * @author AIshmaev
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

    /**
     * Формат даты
     */
    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDateSerializer() {
        this(null);
    }

    private LocalDateSerializer(Class<LocalDate> localDateClass) {
        super(localDateClass);
    }

    /**
     * Записывает в jsonGenerator формат представления даты
     *
     * @param localDate          дата в формате java.util.LocalDate
     * @param jsonGenerator      json generator
     * @param serializerProvider провайдер сериализации
     * @throws IOException
     */
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(localDate));
    }
}
