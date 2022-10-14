package ru.citros.documentflow.factory;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.IncomingDocument;

import java.util.UUID;

/**
 * Реализация интерфейса DocumentCreator
 *
 * @author AIshmaev
 */
@Component
public class IncomingDocumentFactory extends DocumentFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Document create() {
        return IncomingDocument.newBuilder()
                .setId(UUID.randomUUID())
                .setName(randomService.generateLetterRandomString())
                .setText(randomService.generateNumericLetterRandomString())
                .setRegistrationNumber(randomService.generateNumericRandomString())
                .setRegistrationDate(randomService.generateRandomDate())
                .setAuthor(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setSender(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setRecipient(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setOutgoingNumber(randomService.generateNumericRandomString())
                .setOutgoingRegistrationDate(randomService.generateRandomDate())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.INCOMING_DOCUMENT.getName();
    }
}
