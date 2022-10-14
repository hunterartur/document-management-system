package ru.citros.documentflow.dto.organizational_structure;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class PostDto {

    /**
     * Идентификатор должности
     */
    private UUID id;

    /**
     * Название должности
     */
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
