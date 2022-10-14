package ru.citros.documentflow.mapper.organizational_structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.organizational_structure.OrganizationDto;
import ru.citros.documentflow.model.organizational_structure.Organization;

/**
 * Маппинг объекта
 *
 * @author AIshmaev
 */
@Named("OrganizationMapper")
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface OrganizationMapper {

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту
     *
     * @param organization объект приложения
     * @return объект отправки клиенту
     */
    @Named("toDto")
    @Mapping(target = "director", qualifiedByName = {"PersonMapper", "toDtoWithoutDepartment"})
    OrganizationDto toDto(Organization organization);

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту, игнорируя поля director
     *
     * @param organization объект приложения
     * @return объект отправки клиенту
     */
    @Named("toDtoWithoutDirector")
    @Mappings({
            @Mapping(target = "director", ignore = true)
    })
    OrganizationDto toDtoWithoutDirector(Organization organization);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения
     *
     * @param organizationDto объект отправки клиенту
     * @return объект приложения
     */
    @Named("fromDto")
    @Mapping(target = "director", qualifiedByName = {"PersonMapper", "fromDtoWithoutDepartment"})
    Organization fromDto(OrganizationDto organizationDto);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения, игнорируя поля director
     *
     * @param organizationDto объект отправки клиенту
     * @return объект приложения
     */
    @Named("fromDtoWithoutDirector")
    @Mappings({
            @Mapping(target = "director", ignore = true)
    })
    Organization fromDtoWithoutDirector(OrganizationDto organizationDto);
}
