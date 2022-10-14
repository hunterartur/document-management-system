package ru.citros.documentflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ловит и отправляет сообщения об ошибке клиенту
 *
 * @author AIshmaev
 */
@ControllerAdvice
public class ErrorAdvice {

    private final ApiError error;

    public ErrorAdvice(ApiError error) {
        this.error = error;
    }

    /**
     * Ловит ошибки типа DocumentFlowException
     *
     * @param exception исключение
     * @return ошибка
     */
    @ExceptionHandler(DocumentFlowException.class)
    public ResponseEntity<ApiError> handleException(DocumentFlowException exception) {
        fillError("Ошибка при работе приложения", exception);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Ловит ошибки типа IllegalArgumentException
     *
     * @param exception исключение
     * @return ошибка
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleException(IllegalArgumentException exception) {
        fillError("Неправильные аргументы строки поиска", exception);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Ловит ошибки типа NoHandlerFoundException
     *
     * @param exception исключение
     * @return ошибка
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleException(NoHandlerFoundException exception) {
        fillError("Страницы не существует", exception);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Ловит ошибки типа HttpRequestMethodNotSupportedException
     *
     * @param exception исключение
     * @return ошибка
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleException(HttpRequestMethodNotSupportedException exception) {
        fillError("Неправильные параметры метода запроса", exception);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Заполняет объект error
     *
     * @param message   сообщение
     * @param exception исключение
     */
    private void fillError(String message, Exception exception) {
        error.setMessage(message);
        error.setDebugMessage(exception.getMessage());
        List<String> collect = Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        error.setErrors(collect);
    }
}
