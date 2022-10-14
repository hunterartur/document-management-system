package ru.citros.documentflow.factory;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.TaskDocument;

import java.util.UUID;

/**
 * Реализация интерфейса DocumentCreator
 *
 * @author AIshmaev
 */
@Component
public class TaskDocumentFactory extends DocumentFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Document create() {
        return TaskDocument.newBuilder()
                .setId(UUID.randomUUID())
                .setName(randomService.generateLetterRandomString())
                .setContent(randomService.generateNumericLetterRandomString())
                .setRegistrationNumber(randomService.generateNumericRandomString())
                .setRegistrationDate(randomService.generateRandomDate())
                .setAuthor(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setDateIssue(randomService.generateRandomDate())
                .setTermOfExecution(randomService.generateRandomNumber())
                .setExecutor(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setControl(randomService.generateBooleanRandomValue())
                .setController(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.TASK_DOCUMENT.getName();
    }
}
