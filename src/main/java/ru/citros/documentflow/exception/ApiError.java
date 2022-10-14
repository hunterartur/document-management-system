package ru.citros.documentflow.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для отправки сообщения об ошибке клиенту
 *
 * @author AIshmaev
 */
@Component
public class ApiError {

    /**
     * Короткое сообщение
     */
    private String message;

    /**
     * Полное сообщение
     */
    private String debugMessage;

    /**
     * Сами ошибки
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiError() {
    }

    public ApiError(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
