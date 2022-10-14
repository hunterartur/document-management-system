package ru.citros.documentflow.mapper.organizational_structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.organizational_structure.DepartmentDto;
import ru.citros.documentflow.model.organizational_structure.Department;

/**
 * Маппинг объекта
 *
 * @author AIshmaev
 */
@Named("DepartmentMapper")
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface DepartmentMapper {

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту
     *
     * @param department объект приложения
     * @return объект отправки клиенту
     */
    DepartmentDto toDto(Department department);

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту, игнорируя поля director и organization
     *
     * @param department объект приложения
     * @return объект отправки клиенту
     */
    @Named("toDtoWithoutDirectorAndOrganization")
    @Mappings({
            @Mapping(target = "director", ignore = true),
            @Mapping(target = "organization", ignore = true)
    })
    DepartmentDto toDtoWithoutDirectorAndOrganization(Department department);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения
     *
     * @param departmentDto объект отправки клиенту
     * @return объект приложения
     */
    Department fromDto(DepartmentDto departmentDto);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения, игнорируя поля director и organization
     *
     * @param departmentDto объект отправки клиенту
     * @return объект приложения
     */
    @Named("fromDtoWithoutDirectorAndOrganization")
    @Mappings({
            @Mapping(target = "director", ignore = true),
            @Mapping(target = "organization", ignore = true)
    })
    Department fromDtoWithoutDirectorAndOrganization(DepartmentDto departmentDto);
}
