package ru.citros.documentflow.repository.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.service.converter.Converter;

import java.util.List;
import java.util.UUID;

/**
 * Репозитория для дочерних документов
 *
 * @author AIshmaev
 */
public abstract class ChildDocumentRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected RowMapper<Document> documentRowMapper;

    @Autowired
    protected Converter converter;

    /**
     * Сохраняет сущность
     *
     * @param document сущность
     * @return сущность
     */
    abstract Document save(Document document);

    /**
     * Сохраняет список сущностей
     *
     * @param documents сущности
     * @return список
     */
    abstract void saveAll(List<Document> documents);

    /**
     * Обновляет сущность
     *
     * @param document сущность
     */
    abstract Document update(Document document);

    /**
     * Предоставляет сущность из репозитория по uuid
     *
     * @param uuid идентификатор
     * @return объект
     */
    abstract Document getById(UUID uuid);

    /**
     * Возвращает имя типа документа
     *
     * @return имя типа документа
     */
    abstract String getName();

    /**
     * Удаляет сущность
     *
     * @param document сущность
     */
    abstract void delete(Document document);
}
