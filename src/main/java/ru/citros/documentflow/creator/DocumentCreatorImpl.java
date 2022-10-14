package ru.citros.documentflow.creator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.factory.DocumentFactory;
import ru.citros.documentflow.model.document.Document;

import java.util.List;

/**
 * Реализация интерфейса DocumentFactory
 *
 * @author AIshmaev
 */
@Component
public class DocumentCreatorImpl implements DocumentCreator {

    @Autowired
    private List<DocumentFactory> factories;

    /**
     * {@inheritDoc}
     */
    @Override
    public Document create(DocumentType type) {
        return factories.stream()
                .filter(documentCreator -> type.getName().equalsIgnoreCase(documentCreator.getName()))
                .findFirst()
                .orElseThrow(() -> new DocumentFlowException("Ошибка создания документа!"))
                .create();
    }
}
