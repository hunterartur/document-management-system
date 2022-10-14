package ru.citros.documentflow.enumeration;

/**
 * Тип документа
 *
 * @author AIshmaev
 */
public enum DocumentType {

    /**
     * тип Поручение
     */
    TASK_DOCUMENT("TaskDocument"),

    /**
     * тип Входящий документ
     */
    INCOMING_DOCUMENT("IncomingDocument"),

    /**
     * тип Исходящий документ
     */
    OUTGOING_DOCUMENT("OutgoingDocument");

    /**
     * Имя типа документа
     */
    private final String name;

    DocumentType(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя типа документа
     *
     * @return имя типа документа
     */
    public String getName() {
        return name;
    }
}
