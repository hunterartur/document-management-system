package ru.citros.documentflow.mapper.document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.OutgoingDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.OutgoingDocument;

/**
 * Реализует интерфейс ChildDocumentMapper
 *
 * @author AIshmaev
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface OutgoingDocumentMapper extends ChildDocumentMapper<OutgoingDocument, OutgoingDocumentDto> {

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"}),
            @Mapping(target = "sender", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"})
    })
    OutgoingDocumentDto toDto(OutgoingDocument document);

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"}),
            @Mapping(target = "sender", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"})
    })
    OutgoingDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto);

    /**
     * {@inheritDoc}
     */
    default String getName() {
        return "OutgoingDocument";
    }
}
