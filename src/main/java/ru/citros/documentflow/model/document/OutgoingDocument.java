package ru.citros.documentflow.model.document;

import com.fasterxml.jackson.annotation.JsonRootName;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.model.organizational_structure.Person;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа Исходящий документ(OutgoingDocument)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "OutgoingDocument")
public class OutgoingDocument extends Document {

    /**
     * Отправитель документа
     */
    @NotNull(message = "Поле \"Sender\" не заполнено!")
    private Person sender;

    /**
     * Способ доставки документа
     */
    @NotNull(message = "Укажите способ доставки!")
    private DeliveryMethod deliveryMethod;

    public static Builder newBuilder() {
        return new OutgoingDocument().new Builder();
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Исходящий №{0} от {1}г. {2}, способ доставки - {3}", registrationNumber, registrationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), name, deliveryMethod);
    }

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            OutgoingDocument.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            OutgoingDocument.this.name = name;
            return this;
        }

        public Builder setText(String content) {
            OutgoingDocument.this.text = content;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            OutgoingDocument.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            OutgoingDocument.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setAuthor(Person author) {
            OutgoingDocument.this.author = author;
            return this;
        }

        public Builder setSender(Person sender) {
            OutgoingDocument.this.sender = sender;
            return this;
        }

        public Builder setDeliveryMethod(DeliveryMethod deliveryMethod) {
            OutgoingDocument.this.deliveryMethod = deliveryMethod;
            return this;
        }

        public OutgoingDocument build() {
            return OutgoingDocument.this;
        }

    }
}
