package ru.citros.documentflow.factory;

import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.document.OutgoingDocument;

import java.util.UUID;

/**
 * Реализация интерфейса DocumentCreator
 *
 * @author AIshmaev
 */
@Component
public class OutgoingDocumentFactory extends DocumentFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Document create() {
        return OutgoingDocument.newBuilder()
                .setId(UUID.randomUUID())
                .setName(randomService.generateLetterRandomString())
                .setText(randomService.generateNumericLetterRandomString())
                .setRegistrationNumber(randomService.generateNumericRandomString())
                .setRegistrationDate(randomService.generateRandomDate())
                .setAuthor(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setSender(randomService.selectRandomElementFromCollection(personRepository.getAll()))
                .setDeliveryMethod(randomService.generateRandomElementFromArrayOfEnum(DeliveryMethod.values()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DocumentType.OUTGOING_DOCUMENT.getName();
    }
}
