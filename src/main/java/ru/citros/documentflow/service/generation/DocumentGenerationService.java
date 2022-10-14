package ru.citros.documentflow.service.generation;

import ru.citros.documentflow.model.document.Document;

import java.util.List;

/**
 * Интерфейс для генерации документов
 *
 * @author AIshmaev
 */
public interface DocumentGenerationService {

    /**
     * Генерирует список документов из count элементов
     *
     * @param count количество необходимых сгенерированных документов
     * @return список документов
     */
    List<Document> generateDocuments(int count);
}
