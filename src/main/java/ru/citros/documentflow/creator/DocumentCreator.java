package ru.citros.documentflow.creator;

import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;

/**
 * Фабрика по производству документа
 *
 * @author AIshmaev
 */
public interface DocumentCreator {

    /**
     * Создает документ
     *
     * @param type тип документа
     * @return документ
     */
    Document create(DocumentType type);
}
