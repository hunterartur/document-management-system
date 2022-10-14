package ru.citros.documentflow.dto.document;

import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.enumeration.DeliveryMethod;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class OutgoingDocumentDto extends DocumentDto {

    /**
     * Отправитель документа
     */
    private PersonDto sender;

    /**
     * Способ доставки документа
     */
    private DeliveryMethod deliveryMethod;

    public static Builder newBuilder() {
        return new OutgoingDocumentDto().new Builder();
    }

    public PersonDto getSender() {
        return sender;
    }

    public void setSender(PersonDto sender) {
        this.sender = sender;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            OutgoingDocumentDto.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            OutgoingDocumentDto.this.name = name;
            return this;
        }

        public Builder setText(String text) {
            OutgoingDocumentDto.this.text = text;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            OutgoingDocumentDto.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            OutgoingDocumentDto.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setAuthor(PersonDto author) {
            OutgoingDocumentDto.this.author = author;
            return this;
        }

        public Builder setSender(PersonDto sender) {
            OutgoingDocumentDto.this.sender = sender;
            return this;
        }

        public Builder setDeliveryMethod(DeliveryMethod deliveryMethod) {
            OutgoingDocumentDto.this.deliveryMethod = deliveryMethod;
            return this;
        }

        public OutgoingDocumentDto build() {
            return OutgoingDocumentDto.this;
        }

    }
}
