package ru.citros.documentflow.dto.document;

import ru.citros.documentflow.dto.organizational_structure.PersonDto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для отправки клиенту
 *
 * @author AIshmaev
 */
public class TaskDocumentDto extends DocumentDto {

    /**
     * Дата регистрации документа
     */
    private LocalDate dateOfIssue;

    /**
     * Срок исполения документа
     */
    private int termOfExecution;

    /**
     * Исполнитель
     */
    private PersonDto executor;

    /**
     * Признак контрольности
     */
    private boolean control;

    /**
     * Контроллер документа
     */
    private PersonDto controller;

    /**
     * Создает новый объект класса TaskDocument.Builder
     *
     * @return TaskDocument.Builder
     */
    public static Builder newBuilder() {
        return new TaskDocumentDto().new Builder();
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

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public PersonDto getExecutor() {
        return executor;
    }

    public void setExecutor(PersonDto executor) {
        this.executor = executor;
    }

    public PersonDto getController() {
        return controller;
    }

    public void setController(PersonDto controller) {
        this.controller = controller;
    }

    public class Builder {

        private Builder() {

        }

        public Builder setId(UUID id) {
            TaskDocumentDto.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            TaskDocumentDto.this.name = name;
            return this;
        }

        public Builder setContent(String content) {
            TaskDocumentDto.this.text = content;
            return this;
        }

        public Builder setAuthor(PersonDto author) {
            TaskDocumentDto.this.author = author;
            return this;
        }

        public Builder setDateIssue(LocalDate dateIssue) {
            TaskDocumentDto.this.dateOfIssue = dateIssue;
            return this;
        }

        public Builder setTermOfExecution(int dueDate) {
            TaskDocumentDto.this.termOfExecution = dueDate;
            return this;
        }

        public Builder setExecutor(PersonDto executor) {
            TaskDocumentDto.this.executor = executor;
            return this;
        }

        public Builder setControl(boolean control) {
            TaskDocumentDto.this.control = control;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            TaskDocumentDto.this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            TaskDocumentDto.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setController(PersonDto controller) {
            TaskDocumentDto.this.controller = controller;
            return this;
        }

        public DocumentDto build() {
            return TaskDocumentDto.this;
        }

    }

}
