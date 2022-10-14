package ru.citros.documentflow.dto.document;

import ru.citros.documentflow.dto.organizational_structure.PersonDto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class IncomingDocumentDto extends DocumentDto {

    /**
     * Отправитель документа
     */
    private PersonDto sender;

    /**
     * Получатель документа
     */
    private PersonDto recipient;

    /**
     * Исходящий номер
     */
    private String outgoingNumber;

    /**
     * Исходящая дата регистрации
     */
    private LocalDate outgoingRegistrationDate;

    /**
     * Создает новый объект класса IncomingDocument.Builder
     *
     * @return IncomingDocument.Builder
     */
    public static Builder newBuilder() {
        return new IncomingDocumentDto().new Builder();
    }

    public String getOutgoingNumber() {
        return outgoingNumber;
    }

    public PersonDto getSender() {
        return sender;
    }

    public void setSender(PersonDto sender) {
        this.sender = sender;
    }

    public PersonDto getRecipient() {
        return recipient;
    }

    public void setRecipient(PersonDto recipient) {
        this.recipient = recipient;
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

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            IncomingDocumentDto.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            IncomingDocumentDto.this.name = name;
            return this;
        }

        public Builder setText(String content) {
            IncomingDocumentDto.this.text = content;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            IncomingDocumentDto.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            IncomingDocumentDto.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setAuthor(PersonDto author) {
            IncomingDocumentDto.this.author = author;
            return this;
        }

        public Builder setSender(PersonDto sender) {
            IncomingDocumentDto.this.sender = sender;
            return this;
        }

        public Builder setRecipient(PersonDto recipient) {
            IncomingDocumentDto.this.recipient = recipient;
            return this;
        }

        public Builder setOutgoingNumber(String outNumber) {
            IncomingDocumentDto.this.outgoingNumber = outNumber;
            return this;
        }

        public Builder setOutgoingRegistrationDate(LocalDate outRegDate) {
            IncomingDocumentDto.this.outgoingRegistrationDate = outRegDate;
            return this;
        }

        public IncomingDocumentDto build() {
            return IncomingDocumentDto.this;
        }
    }
}
