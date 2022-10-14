package ru.citros.documentflow.mapper.document;

import ru.citros.documentflow.dto.document.DocumentDto;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.model.document.Document;

/**
 * Определяет интерфейс маппинга объектов
 *
 * @param <T> тип объекта приложения
 * @param <V> тип объекта отправки клиенту
 * @author AIshmaev
 */
public interface ChildDocumentMapper<T extends Document, V extends DocumentDto> {

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту
     *
     * @param document объект приложения
     * @return объект отправки клиенту
     */
    V toDto(T document);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения
     *
     * @param documentDto объект отправки клиенту
     * @return объект приложения
     */
    T fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto);

    /**
     * Возвращает имя типа документа
     *
     * @return имя типа документа
     */
    String getName();
}
