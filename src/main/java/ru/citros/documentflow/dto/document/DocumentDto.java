package ru.citros.documentflow.dto.document;

import ru.citros.documentflow.dto.organizational_structure.PersonDto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public abstract class DocumentDto {

    /**
     * Идентификатор документа
     */
    protected UUID id;

    /**
     * Наименование документа
     */
    protected String name;

    /**
     * Текст документа
     */
    protected String text;

    /**
     * Регистрационный номер документа
     */
    protected String registrationNumber;

    /**
     * Дата регистрации документа
     */
    protected LocalDate registrationDate;

    /**
     * Автор документа
     */
    protected PersonDto author;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public PersonDto getAuthor() {
        return author;
    }

    public void setAuthor(PersonDto author) {
        this.author = author;
    }
}
