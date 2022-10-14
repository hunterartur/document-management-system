package ru.citros.documentflow.mapper.document;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.TaskDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.TaskDocument;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class TaskDocumentMapperImpl implements TaskDocumentMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public TaskDocumentDto toDto(TaskDocument document) {
        if ( document == null ) {
            return null;
        }

        TaskDocumentDto taskDocumentDto = new TaskDocumentDto();

        taskDocumentDto.setAuthor( personMapper.toDtoWithoutDepartment( document.getAuthor() ) );
        taskDocumentDto.setExecutor( personMapper.toDtoWithoutDepartment( document.getExecutor() ) );
        taskDocumentDto.setController( personMapper.toDtoWithoutDepartment( document.getController() ) );
        taskDocumentDto.setId( document.getId() );
        taskDocumentDto.setName( document.getName() );
        taskDocumentDto.setText( document.getText() );
        taskDocumentDto.setRegistrationNumber( document.getRegistrationNumber() );
        taskDocumentDto.setRegistrationDate( document.getRegistrationDate() );
        taskDocumentDto.setDateOfIssue( document.getDateOfIssue() );
        taskDocumentDto.setTermOfExecution( document.getTermOfExecution() );
        taskDocumentDto.setControl( document.isControl() );

        return taskDocumentDto;
    }

    @Override
    public TaskDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto) {
        if ( documentDto == null ) {
            return null;
        }

        TaskDocument taskDocument = new TaskDocument();

        taskDocument.setAuthor( personMapper.fromDtoWithoutDepartment( documentDto.getAuthor() ) );
        taskDocument.setExecutor( personMapper.fromDtoWithoutDepartment( documentDto.getExecutor() ) );
        taskDocument.setController( personMapper.fromDtoWithoutDepartment( documentDto.getController() ) );
        taskDocument.setId( documentDto.getId() );
        taskDocument.setName( documentDto.getName() );
        taskDocument.setText( documentDto.getText() );
        taskDocument.setRegistrationNumber( documentDto.getRegistrationNumber() );
        taskDocument.setRegistrationDate( documentDto.getRegistrationDate() );
        taskDocument.setDateOfIssue( documentDto.getDateOfIssue() );
        taskDocument.setTermOfExecution( documentDto.getTermOfExecution() );
        taskDocument.setControl( documentDto.isControl() );

        return taskDocument;
    }
}
