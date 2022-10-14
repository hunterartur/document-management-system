package ru.citros.documentflow.model.organizational_structure;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа отдел(Department)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "Department")
@XmlRootElement
@XmlType(propOrder = {"id", "fullName", "shortName", "director", "contactPhoneNumbers", "organization"})
public class Department extends Staff {

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

    /**
     * Организация
     */
    @NotBlank(message = "Организация не может быть null")
    private Organization organization;

    public static Builder newBuilder() {
        return new Department().new Builder();
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Department: id={0}, fullName={1}, shortName={2}, director={3}, organization={4}, contactPhoneNumbers={5}",
                id, fullName, shortName, director, organization, contactPhoneNumbers);
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            Department.this.id = id;
            return this;
        }

        public Builder setFullName(String fullName) {
            Department.this.fullName = fullName;
            return this;
        }

        public Builder setShortName(String shortName) {
            Department.this.shortName = shortName;
            return this;
        }

        public Builder setDirector(Person director) {
            Department.this.director = director;
            return this;
        }

        public Builder setContactPhoneNumbers(List<String> contactPhoneNumbers) {
            Department.this.contactPhoneNumbers = contactPhoneNumbers;
            return this;
        }

        public Builder setOrganization(Organization organization) {
            Department.this.organization = organization;
            return this;
        }

        public Department build() {
            return Department.this;
        }
    }
}
