package ru.citros.documentflow.model.document;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.citros.documentflow.jackson_util.LocalDateSerializer;
import ru.citros.documentflow.model.organizational_structure.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Сущность которая хранит данные типа Поручение(TaskDocument)
 *
 * @author AIshmaev
 */
@JsonRootName(value = "TaskDocument")
public class TaskDocument extends Document implements Cloneable {

    /**
     * Дата регистрации документа
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @PastOrPresent(message = "Дата выдачи заказа должна быть меньше или равна сегодняшней дате!")
    private LocalDate dateOfIssue;

    /**
     * Срок исполения документа
     */
    @PositiveOrZero(message = "Срок исполнения распоряжения не может быть отрицательным")
    private int termOfExecution;

    /**
     * Исполнитель
     */
    @NotNull(message = "Поле \"Executor\" не заполено!")
    private Person executor;

    /**
     * Признак контрольности
     */
    private boolean control;

    /**
     * Контроллер документа
     */
    @NotNull(message = "Поле \"Controller\" не заполено!")
    private Person controller;

    /**
     * Создает новый объект класса TaskDocument.Builder
     *
     * @return TaskDocument.Builder
     */
    public static Builder newBuilder() {
        return new TaskDocument().new Builder();
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public int getTermOfExecution() {
        return termOfExecution;
    }

    public void setTermOfExecution(int termOfExecution) {
        this.termOfExecution = termOfExecution;
    }

    public Person getExecutor() {
        return executor;
    }

    public void setExecutor(Person executor) {
        this.executor = executor;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public Person getController() {
        return controller;
    }

    public void setController(Person controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Поручение №{0} от {1}г. {2}", registrationNumber, registrationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), name);
    }

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            TaskDocument.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            TaskDocument.this.name = name;
            return this;
        }

        public Builder setContent(String content) {
            TaskDocument.this.text = content;
            return this;
        }

        public Builder setAuthor(Person author) {
            TaskDocument.this.author = author;
            return this;
        }

        public Builder setDateIssue(LocalDate dateIssue) {
            TaskDocument.this.dateOfIssue = dateIssue;
            return this;
        }

        public Builder setTermOfExecution(int dueDate) {
            TaskDocument.this.termOfExecution = dueDate;
            return this;
        }

        public Builder setExecutor(Person executor) {
            TaskDocument.this.executor = executor;
            return this;
        }

        public Builder setControl(boolean control) {
            TaskDocument.this.control = control;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            TaskDocument.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            TaskDocument.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setController(Person controller) {
            TaskDocument.this.controller = controller;
            return this;
        }

        public Document build() {
            return TaskDocument.this;
        }

    }
}
