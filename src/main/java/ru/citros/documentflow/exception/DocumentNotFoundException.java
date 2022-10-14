package ru.citros.documentflow.exception;

/**
 * Исключение, которое нужно выбросить если документ не был найден
 *
 * @author AIshmaev
 */
public class DocumentNotFoundException extends RuntimeException {

    /**
     * Генерирует исключение
     *
     * @param message сообщение
     */
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
