package ru.citros.documentflow.dto.organizational_structure;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class PersonDto extends StaffDto {

    /**
     * Имя
     */
    private String firstName;

    /**
     * Отчество
     */
    private String patronymic;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Должность
     */
    private PostDto post;

    /**
     * Отдел
     */
    private DepartmentDto department;

    /**
     * Фото
     */
    private String photo;

    /**
     * День рождения
     */
    private LocalDate birthday;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    public static Builder newBuilder() {
        return new PersonDto().new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            PersonDto.this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            PersonDto.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            PersonDto.this.lastName = lastName;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            PersonDto.this.patronymic = patronymic;
            return this;
        }

        public Builder setPost(PostDto postDto) {
            PersonDto.this.post = postDto;
            return this;
        }

        public Builder setPhoto(String photo) {
            PersonDto.this.photo = photo;
            return this;
        }

        public Builder setBirthday(LocalDate birthday) {
            PersonDto.this.birthday = birthday;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            PersonDto.this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setDepartment(DepartmentDto departmentDto) {
            PersonDto.this.department = departmentDto;
            return this;
        }

        public PersonDto build() {
            return PersonDto.this;
        }
    }
}
