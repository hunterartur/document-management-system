package ru.citros.documentflow.exception;

/**
 * Исключение, которое нужно выбросить если документ существует где либо
 *
 * @author AIshmaev
 */
public class DocumentExistsException extends RuntimeException {

    /**
     * Генерирует исключение
     *
     * @param message сообщение
     */
    public DocumentExistsException(String message) {
        super(message);
    }
}
