package ru.citros.documentflow.service.converter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Конвертер
 *
 * @author AIshmaev
 */
public interface Converter {

    /**
     * Конвертирует Date на LocalDate
     *
     * @param date дата в формате Date
     * @return дата в формате LocalDate
     */
    LocalDate convertDateToLocalDate(Date date);

    /**
     * Конвертирует LocalDate на Date
     *
     * @param localDate дата в формате LocalDate
     * @return дата в формате Date
     */
    Date convertLocalDateToDate(LocalDate localDate);

    /**
     * Конвертирует строковый id в uuid
     *
     * @param id строковый id
     * @return uuid
     */
    UUID convertStringIdToUuid(String id);

    /**
     * Преобразует uuid в строковыфй формат
     *
     * @param uuid uuid
     * @return строка
     */
    String convertUuidToString(UUID uuid);
}
