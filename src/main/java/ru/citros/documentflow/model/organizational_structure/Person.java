package ru.citros.documentflow.model.organizational_structure;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.citros.documentflow.jackson_util.LocalDateSerializer;
import ru.citros.documentflow.jaxb_util.LocalDateAdapter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа сотрудник(Person)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "Person")
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "firstName", "lastName", "patronymic", "post", "photo", "birthday", "phoneNumber", "department"})
public class Person extends Staff {

    /**
     * Имя
     */
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, message = "Минимальная длина имени 2 символа")
    private String firstName;

    /**
     * Отчество
     */
    @NotBlank(message = "Отчество не может быть пустым")
    @Size(min = 2, message = "Минимальная длина отчества 2 символа")
    private String patronymic;

    /**
     * Фамилия
     */
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, message = "Минимальная длина фамилии 2 символа")
    private String lastName;

    /**
     * Должность
     */
    @NotNull(message = "Должность не может быть null")
    private Post post;

    /**
     * Отдел
     */
    @NotNull(message = "Отдел не может быть null")
    private Department department;

    /**
     * Фото
     */
    private String photo;

    /**
     * День рождения
     */
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    public static Builder newBuilder() {
        return new Person().new Builder();
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Person: id={0}, firstName={1}, lastName={2}, patronymic={3}, post={4}, photo={5}, birthday={6}, phoneNumber={7}, department={8}",
                id, firstName, patronymic, lastName, post, photo, birthday, phoneNumber, department);
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(UUID id) {
            Person.this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            Person.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            Person.this.lastName = lastName;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            Person.this.patronymic = patronymic;
            return this;
        }

        public Builder setPost(Post post) {
            Person.this.post = post;
            return this;
        }

        public Builder setPhoto(String photo) {
            Person.this.photo = photo;
            return this;
        }

        public Builder setBirthday(LocalDate birthday) {
            Person.this.birthday = birthday;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            Person.this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setDepartment(Department department) {
            Person.this.department = department;
            return this;
        }

        public Person build() {
            return Person.this;
        }
    }
}
