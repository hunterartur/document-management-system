package ru.citros.documentflow.mapper.document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.dto.document.TaskDocumentDto;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.document.TaskDocument;

/**
 * Реализует интерфейс ChildDocumentMapper
 *
 * @author AIshmaev
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface TaskDocumentMapper extends ChildDocumentMapper<TaskDocument, TaskDocumentDto> {

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"}),
            @Mapping(target = "executor", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"}),
            @Mapping(target = "controller", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"})
    })
    TaskDocumentDto toDto(TaskDocument document);

    /**
     * {@inheritDoc}
     */
    @Mappings({
            @Mapping(target = "author", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"}),
            @Mapping(target = "executor", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"}),
            @Mapping(target = "controller", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"})
    })
    TaskDocument fromDto(DocumentWithAllFieldsOfAllDocumentsDto documentDto);

    /**
     * {@inheritDoc}
     */
    default String getName() {
        return "TaskDocument";
    }
}
