package ru.citros.documentflow.model.organizational_structure;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа должность(Post)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "Post")
@XmlRootElement(name = "post")
@XmlType(propOrder = {"id", "name"})
public class Post {

    /**
     * Идентификатор должности
     */
    @NotNull(message = "Id не может быть равен null")
    private UUID id;

    /**
     * Название должности
     */
    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 2, message = "Минимальная длина названия 2 символа")
    private String name;

    public Post() {
    }

    public Post(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return MessageFormat.format("Post: id={0}, name={1}", id, name);
    }
}
