package ru.citros.documentflow.dto.organizational_structure;

import java.util.List;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class OrganizationDto extends StaffDto {

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

    public static Builder newBuilder() {
        return new OrganizationDto().new Builder();
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

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            OrganizationDto.this.id = id;
            return this;
        }

        public Builder setFullName(String fullName) {
            OrganizationDto.this.fullName = fullName;
            return this;
        }

        public Builder setShortName(String shortName) {
            OrganizationDto.this.shortName = shortName;
            return this;
        }

        public Builder setDirector(PersonDto personDto) {
            OrganizationDto.this.director = personDto;
            return this;
        }

        public Builder setContactPhoneNumbers(List<String> contactPhoneNumbers) {
            OrganizationDto.this.contactPhoneNumbers = contactPhoneNumbers;
            return this;
        }

        public OrganizationDto build() {
            return OrganizationDto.this;
        }
    }
}
