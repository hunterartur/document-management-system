package ru.citros.documentflow.service.converter;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Конвертер
 *
 * @author AIshmaev
 */
@Service
public class ConverterImpl implements Converter {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate convertDateToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date convertLocalDateToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID convertStringIdToUuid(String id) {
        return (id != null) ? UUID.fromString(id) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertUuidToString(UUID uuid) {
        return (uuid != null) ? uuid.toString() : null;
    }
}
