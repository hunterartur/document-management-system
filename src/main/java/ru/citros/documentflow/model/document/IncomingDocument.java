package ru.citros.documentflow.model.document;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.citros.documentflow.jackson_util.LocalDateSerializer;
import ru.citros.documentflow.model.organizational_structure.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа входящий документ(IncomingDocument)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "IncomingDocument")
public class IncomingDocument extends Document {

    /**
     * Отправитель документа
     */
    @NotNull(message = "Поле \"Sender\" не заполнено!")
    private Person sender;

    /**
     * Получатель документа
     */
    @NotNull(message = "Поле \"Recipient\" не заполнено!")
    private Person recipient;

    /**
     * Исходящий номер
     */
    @NotNull(message = "Поле \"Outgoing number\" не заполнено!")
    private String outgoingNumber;

    /**
     * Исходящая дата регистрации
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @PastOrPresent(message = "Дата исходящей регистрации должна быть меньше или равна сегодняшней дате!")
    private LocalDate outgoingRegistrationDate;

    /**
     * Создает новый объект класса IncomingDocument.Builder
     *
     * @return IncomingDocument.Builder
     */
    public static Builder newBuilder() {
        return new IncomingDocument().new Builder();
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public String getOutgoingNumber() {
        return outgoingNumber;
    }

    public void setOutgoingNumber(String outgoingNumber) {
        this.outgoingNumber = outgoingNumber;
    }

    public LocalDate getOutgoingRegistrationDate() {
        return outgoingRegistrationDate;
    }

    public void setOutgoingRegistrationDate(LocalDate outgoingRegistrationDate) {
        this.outgoingRegistrationDate = outgoingRegistrationDate;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Входящий №{0} от {1}г. {2}", registrationNumber, registrationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), name);
    }

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            IncomingDocument.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            IncomingDocument.this.name = name;
            return this;
        }

        public Builder setText(String content) {
            IncomingDocument.this.text = content;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            IncomingDocument.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            IncomingDocument.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setAuthor(Person author) {
            IncomingDocument.this.author = author;
            return this;
        }

        public Builder setSender(Person sender) {
            IncomingDocument.this.sender = sender;
            return this;
        }

        public Builder setRecipient(Person recipient) {
            IncomingDocument.this.recipient = recipient;
            return this;
        }

        public Builder setOutgoingNumber(String outNumber) {
            IncomingDocument.this.outgoingNumber = outNumber;
            return this;
        }

        public Builder setOutgoingRegistrationDate(LocalDate outRegDate) {
            IncomingDocument.this.outgoingRegistrationDate = outRegDate;
            return this;
        }

        public IncomingDocument build() {
            return IncomingDocument.this;
        }
    }
}
