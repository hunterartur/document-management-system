package ru.citros.documentflow.mapper.document;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.OutgoingDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.OutgoingDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class OutgoingDocumentMapperImpl implements OutgoingDocumentMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public OutgoingDocumentDto toDto(OutgoingDocument document) {
        if ( document == null ) {
            return null;
        }

        OutgoingDocumentDto.Builder outgoingDocumentDto = OutgoingDocumentDto.newBuilder();

        outgoingDocumentDto.setAuthor( personMapper.toDtoWithoutDepartment( document.getAuthor() ) );
        outgoingDocumentDto.setSender( personMapper.toDtoWithoutDepartment( document.getSender() ) );
        outgoingDocumentDto.setId( document.getId() );
        outgoingDocumentDto.setName( document.getName() );
        outgoingDocumentDto.setText( document.getText() );
        outgoingDocumentDto.setRegistrationNumber( document.getRegistrationNumber() );
        outgoingDocumentDto.setRegistrationDate( document.getRegistrationDate() );
        outgoingDocumentDto.setDeliveryMethod( document.getDeliveryMethod() );

        return outgoingDocumentDto.build();
    }

    @Override
    public OutgoingDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto) {
        if ( documentDto == null ) {
            return null;
        }

        OutgoingDocument.Builder outgoingDocument = OutgoingDocument.newBuilder();

        outgoingDocument.setAuthor( personMapper.fromDtoWithoutDepartment( documentDto.getAuthor() ) );
        outgoingDocument.setSender( personMapper.fromDtoWithoutDepartment( documentDto.getSender() ) );
        outgoingDocument.setId( documentDto.getId() );
        outgoingDocument.setName( documentDto.getName() );
        outgoingDocument.setText( documentDto.getText() );
        outgoingDocument.setRegistrationNumber( documentDto.getRegistrationNumber() );
        outgoingDocument.setRegistrationDate( documentDto.getRegistrationDate() );
        outgoingDocument.setDeliveryMethod( documentDto.getDeliveryMethod() );

        return outgoingDocument.build();
    }
}
