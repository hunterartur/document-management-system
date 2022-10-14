package ru.citros.documentflow.mapper.document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.IncomingDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.IncomingDocument;

/**
 * Реализует интерфейс ChildDocumentMapper
 *
 * @author AIshmaev
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface IncomingDocumentMapper extends ChildDocumentMapper<IncomingDocument, IncomingDocumentDto> {

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"}),
            @Mapping(target = "sender", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"}),
            @Mapping(target = "recipient", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"})
    })
    IncomingDocumentDto toDto(IncomingDocument document);

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"}),
            @Mapping(target = "sender", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"}),
            @Mapping(target = "recipient", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"})
    })
    IncomingDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto);

    /**
     * {@inheritDoc}
     */
    default String getName() {
        return "IncomingDocument";
    }
}
