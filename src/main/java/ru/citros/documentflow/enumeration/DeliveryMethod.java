package ru.citros.documentflow.enumeration;

/**
 * Метод доставки документов
 *
 * @author AIshmaev
 */
public enum DeliveryMethod {

    /**
     * Электронная почта
     */
    EMAIL("Email"),

    /**
     * Почта России
     */
    RUSSIAN_POST("RussianPost"),

    /**
     * Диадок
     */
    DIADOC("Diadoc"),

    /**
     * СБИС
     */
    SBIS("SBIS"),

    /**
     * Курьер
     */
    COURIER("Courier");

    /**
     * Название метода доставки
     */
    private String name;

    DeliveryMethod(String name) {
        this.name = name;
    }

    /**
     * Возвращает метод доставки
     *
     * @return метод доставки
     */
    public String getName() {
        return name;
    }
}
