package ru.citros.documentflow.service.validate;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Валидация обектов.
 *
 * @author AIshmaev
 */
@Service
public class ValidateServiceImpl<T> implements ValidateService<T> {

    /**
     * Объект валидатор
     */
    private Validator validator;

    public ValidateServiceImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validate(T object) {
        return setToString(validator.validate(object));
    }

    /**
     * Преобразует множество элементов в строку элементов
     *
     * @param set множество элементов
     * @return строка элементов
     */
    private String setToString(Set<ConstraintViolation<T>> set) {
        return set
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
    }
}
