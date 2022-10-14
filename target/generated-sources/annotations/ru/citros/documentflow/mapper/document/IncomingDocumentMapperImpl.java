package ru.citros.documentflow.mapper.document;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.IncomingDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.IncomingDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class IncomingDocumentMapperImpl implements IncomingDocumentMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public IncomingDocumentDto toDto(IncomingDocument document) {
        if ( document == null ) {
            return null;
        }

        IncomingDocumentDto.Builder incomingDocumentDto = IncomingDocumentDto.newBuilder();

        incomingDocumentDto.setAuthor( personMapper.toDtoWithoutDepartment( document.getAuthor() ) );
        incomingDocumentDto.setSender( personMapper.toDtoWithoutDepartment( document.getSender() ) );
        incomingDocumentDto.setRecipient( personMapper.toDtoWithoutDepartment( document.getRecipient() ) );
        incomingDocumentDto.setId( document.getId() );
        incomingDocumentDto.setName( document.getName() );
        incomingDocumentDto.setText( document.getText() );
        incomingDocumentDto.setRegistrationNumber( document.getRegistrationNumber() );
        incomingDocumentDto.setRegistrationDate( document.getRegistrationDate() );
        incomingDocumentDto.setOutgoingNumber( document.getOutgoingNumber() );
        incomingDocumentDto.setOutgoingRegistrationDate( document.getOutgoingRegistrationDate() );

        return incomingDocumentDto.build();
    }

    @Override
    public IncomingDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto) {
        if ( documentDto == null ) {
            return null;
        }

        IncomingDocument.Builder incomingDocument = IncomingDocument.newBuilder();

        incomingDocument.setAuthor( personMapper.fromDtoWithoutDepartment( documentDto.getAuthor() ) );
        incomingDocument.setSender( personMapper.fromDtoWithoutDepartment( documentDto.getSender() ) );
        incomingDocument.setRecipient( personMapper.fromDtoWithoutDepartment( documentDto.getRecipient() ) );
        incomingDocument.setId( documentDto.getId() );
        incomingDocument.setName( documentDto.getName() );
        incomingDocument.setText( documentDto.getText() );
        incomingDocument.setRegistrationNumber( documentDto.getRegistrationNumber() );
        incomingDocument.setRegistrationDate( documentDto.getRegistrationDate() );
        incomingDocument.setOutgoingNumber( documentDto.getOutgoingNumber() );
        incomingDocument.setOutgoingRegistrationDate( documentDto.getOutgoingRegistrationDate() );

        return incomingDocument.build();
    }
}
