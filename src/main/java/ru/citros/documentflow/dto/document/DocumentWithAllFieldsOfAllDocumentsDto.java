package ru.citros.documentflow.dto.document;

import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.enumeration.DocumentType;

import java.time.LocalDate;

/**
 * Упрощенный и обобщенный класс для отправки клиенту, содержащий все поля классов для маппинга
 *
 * @author AIshmaev
 */
public class DocumentWithAllFieldsOfAllDocumentsDto extends DocumentDto {

    /**
     * Отправитель документа
     */
    private PersonDto sender;

    /**
     * Получатель документа
     */
    private PersonDto recipient;

    /**
     * Исходящий номер
     */
    private String outgoingNumber;

    /**
     * Исходящая дата регистрации
     */
    private LocalDate outgoingRegistrationDate;

    /**
     * Способ доставки документа
     */
    private DeliveryMethod deliveryMethod;

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
     * Тип документа
     */
    private DocumentType documentType;

    public PersonDto getSender() {
        return sender;
    }

    public void setSender(PersonDto sender) {
        this.sender = sender;
    }

    public PersonDto getRecipient() {
        return recipient;
    }

    public void setRecipient(PersonDto recipient) {
        this.recipient = recipient;
    }

    public String getOutgoingNumber() {
        return outgoingNumber;
    }

    public void setOutgoingNumber(String outgoingNumber) {
        this.outgoingNumber = outgoingNumber;
    }

    public LocalDate getOutgoingRegistrationDate() {
        return outgoingRegistrationDate;
    }

    public void setOutgoingRegistrationDate(LocalDate outgoingRegistrationDate) {
        this.outgoingRegistrationDate = outgoingRegistrationDate;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
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

    public PersonDto getExecutor() {
        return executor;
    }

    public void setExecutor(PersonDto executor) {
        this.executor = executor;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public PersonDto getController() {
        return controller;
    }

    public void setController(PersonDto controller) {
        this.controller = controller;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
