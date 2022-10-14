package ru.citros.documentflow.exception;

/**
 * Общее исключение приложения
 *
 * @author AIshmaev
 */
public class DocumentFlowException extends RuntimeException {

    public DocumentFlowException(String message) {
        super(message);
    }

    public DocumentFlowException(String message, Throwable exception) {
        super(message, exception);
    }
}
