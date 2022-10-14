package ru.citros.documentflow.model.document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.citros.documentflow.jackson_util.LocalDateSerializer;
import ru.citros.documentflow.model.organizational_structure.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;

/**
 * Абстрактная сущность которая описывает общие свойства документов типа Входящий документ(IncomingDocument), Исходящий документ(OutgoingDocument), Поручение(TaskDocument)
 *
 * @author AIshmaev
 */
public abstract class Document implements Comparable<Document> {

    /**
     * Идентификатор документа
     */
    @NotNull(message = "Поле \"id\" не заполнено!")
    protected UUID id;

    /**
     * Наименование документа
     */
    @Size(min = 2, message = "Минимальная длина названия 2 символа!")
    protected String name;

    /**
     * Текст документа
     */
    @Size(min = 5, message = "Минимальная длина текста 5 символов!")
    protected String text;

    /**
     * Регистрационный номер документа
     */
    @Size(min = 5, message = "Минимальная длина регистрационного номера 5 символов!")
    protected String registrationNumber;

    /**
     * Дата регистрации документа
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @PastOrPresent(message = "Дата регистрации должна быть меньше или равна сегодняшней дате!")
    protected LocalDate registrationDate;

    /**
     * Автор документа
     */
    @NotNull(message = "Поле \"Author\" не заполнено!")
    protected Person author;

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

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Document id={0}, name={1}, content={2}, registrationNumber={3}, registrationDate={4}, author={5}",
                id, name, text, registrationNumber, registrationDate, author);
    }

    @Override
    public int compareTo(Document o) {
        return Comparator.comparing(Document::getRegistrationNumber).thenComparing(Document::getRegistrationDate).compare(this, o);
    }

}
