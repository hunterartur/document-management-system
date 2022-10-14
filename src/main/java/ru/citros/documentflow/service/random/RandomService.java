package ru.citros.documentflow.service.random;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс DocumentService предоставляет инструменты для работы с документом и списком документов.
 *
 * @author AIshmaev
 */
public interface RandomService {

    /**
     * Генерирует цифро-буквенную строку
     *
     * @return случайная цифро-буквенная строка
     */
    String generateNumericLetterRandomString();

    /**
     * Генерирует буквенную строку
     *
     * @return случайная буквенная строка
     */
    String generateLetterRandomString();

    /**
     * Генерирует цифровую строку
     *
     * @return случайная цифровая строка
     */
    String generateNumericRandomString();

    /**
     * Генерирует булевское значение
     *
     * @return случайное булевское значение
     */
    boolean generateBooleanRandomValue();

    /**
     * Генерирует дату
     *
     * @return случайная дата
     */
    LocalDate generateRandomDate();

    /**
     * Генерирует цифру
     *
     * @return случайная цифра
     */
    int generateRandomNumber();

    /**
     * Выбирает случайный элемент из коллекции
     *
     * @return случайный элемент из коллекции
     */
    <T> T selectRandomElementFromCollection(List<? extends T> collection);

    /**
     * Генерирует случайный элемент из переданного массива перечислений
     *
     * @param values массив из перечислений
     * @param <T>    тип перечисления
     * @return случайное перечисление
     */
    <T extends Enum<T>> T generateRandomElementFromArrayOfEnum(T[] values);
}
