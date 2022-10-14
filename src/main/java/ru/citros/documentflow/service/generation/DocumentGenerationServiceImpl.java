package ru.citros.documentflow.service.generation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.creator.DocumentCreator;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.service.random.RandomService;
import ru.citros.documentflow.service.validate.ValidateService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Генерирует документов
 *
 * @author AIshmaev
 */
@Service
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private DocumentCreator documentCreator;

    @Autowired
    private RandomService randomService;

    @Autowired
    private ValidateService<Document> validateService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Document> generateDocuments(int count) {
        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Document document = documentCreator.create(generateDocumentType());
            String validateString = validateService.validate(document);
            if (!validateString.isEmpty()) {
                logger.error(MessageFormat.format("Ошибка при генерации случайного документа: {0} Документ не будет сохранен!",
                        validateString));
                continue;
            }
            documents.add(document);
        }
        return documents;
    }

    /**
     * Генерирует тип случайный тип документа
     *
     * @return случайный тип документа
     */
    private DocumentType generateDocumentType() {
        return randomService.generateRandomElementFromArrayOfEnum(DocumentType.values());
    }
}
