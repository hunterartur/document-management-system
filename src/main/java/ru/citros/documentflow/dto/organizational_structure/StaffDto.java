package ru.citros.documentflow.dto.organizational_structure;

import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public abstract class StaffDto {

    /**
     * Идентификатор
     */
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
