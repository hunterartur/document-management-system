package ru.citros.documentflow.model.organizational_structure;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа организация(Organization)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "Organization")
@XmlRootElement
@XmlType(propOrder = {"id", "fullName", "shortName", "director", "contactPhoneNumbers"})
public class Organization extends Staff {

    /**
     * Полное имя отдела
     */
    @NotBlank(message = "Полное наименование не может быть пустым")
    @Size(min = 3, message = "Минимальная длина короткого наименования 3 символа")
    private String fullName;

    /**
     * Сокращенное имя отдела
     */
    @NotBlank(message = "Короткое наименование не может быть пустым")
    @Size(min = 3, message = "Минимальная длина короткого наименования 3 символа")
    private String shortName;

    /**
     * Руководитель
     */
    @NotBlank(message = "Директор не может быть null")
    private Person director;

    /**
     * Список контактных номеров
     */
    @NotBlank(message = "Не заполнены контактные номера")
    private List<String> contactPhoneNumbers;

    public static Builder newBuilder() {
        return new Organization().new Builder();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<String> getContactPhoneNumbers() {
        return contactPhoneNumbers;
    }

    public void setContactPhoneNumbers(List<String> contactPhoneNumbers) {
        this.contactPhoneNumbers = contactPhoneNumbers;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Organization: id={0}, fullName={1}, shortName={2}, director={3}, contactPhoneNumbers={4}",
                id, fullName, shortName, director, contactPhoneNumbers);
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            Organization.this.id = id;
            return this;
        }

        public Builder setFullName(String fullName) {
            Organization.this.fullName = fullName;
            return this;
        }

        public Builder setShortName(String shortName) {
            Organization.this.shortName = shortName;
            return this;
        }

        public Builder setDirector(Person director) {
            Organization.this.director = director;
            return this;
        }

        public Builder setContactPhoneNumbers(List<String> contactPhoneNumbers) {
            Organization.this.contactPhoneNumbers = contactPhoneNumbers;
            return this;
        }

        public Organization build() {
            return Organization.this;
        }
    }
}
