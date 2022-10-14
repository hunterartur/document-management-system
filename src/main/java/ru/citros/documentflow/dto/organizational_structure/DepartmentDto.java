package ru.citros.documentflow.dto.organizational_structure;

import java.util.List;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class DepartmentDto extends StaffDto {

    /**
     * Полное имя отдела
     */
    private String fullName;

    /**
     * Сокращенное имя отдела
     */
    private String shortName;

    /**
     * Руководитель
     */
    private PersonDto director;

    /**
     * Список контактных номеров
     */
    private List<String> contactPhoneNumbers;

    /**
     * Организация
     */
    private OrganizationDto organization;

    public static DepartmentDto.Builder newBuilder() {
        return new DepartmentDto().new Builder();
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

    public List<String> getContactPhoneNumbers() {
        return contactPhoneNumbers;
    }

    public void setContactPhoneNumbers(List<String> contactPhoneNumbers) {
        this.contactPhoneNumbers = contactPhoneNumbers;
    }

    public PersonDto getDirector() {
        return director;
    }

    public void setDirector(PersonDto director) {
        this.director = director;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            DepartmentDto.this.id = id;
            return this;
        }

        public Builder setFullName(String fullName) {
            DepartmentDto.this.fullName = fullName;
            return this;
        }

        public Builder setShortName(String shortName) {
            DepartmentDto.this.shortName = shortName;
            return this;
        }

        public Builder setDirector(PersonDto personDto) {
            DepartmentDto.this.director = personDto;
            return this;
        }

        public Builder setContactPhoneNumbers(List<String> contactPhoneNumbers) {
            DepartmentDto.this.contactPhoneNumbers = contactPhoneNumbers;
            return this;
        }

        public Builder setOrganization(OrganizationDto organizationDto) {
            DepartmentDto.this.organization = organizationDto;
            return this;
        }

        public DepartmentDto build() {
            return DepartmentDto.this;
        }
    }
}
