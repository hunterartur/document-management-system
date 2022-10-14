package ru.citros.documentflow.mapper.organizational_structure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.model.organizational_structure.Person;

/**
 * Маппинг объекта
 *
 * @author AIshmaev
 */
@Named("PersonMapper")
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, PostMapper.class})
public interface PersonMapper {

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту
     *
     * @param person объект приложения
     * @return объект отправки клиенту
     */
    @Named("toDto")
    @Mapping(target = "department", qualifiedByName = {"DepartmentMapper", "toDtoWithoutDirectorAndOrganization"})
    PersonDto toDto(Person person);

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту, игнорируя поля department
     *
     * @param person объект приложения
     * @return объект отправки клиенту
     */
    @Named("toDtoWithoutDepartment")
    @Mapping(target = "department", ignore = true)
    PersonDto toDtoWithoutDepartment(Person person);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения
     *
     * @param personDto объект отправки клиенту
     * @return объект приложения
     */
    @Named("fromDto")
    @Mapping(target = "department", qualifiedByName = {"DepartmentMapper", "fromDtoWithoutDirectorAndOrganization"})
    Person fromDto(PersonDto personDto);

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения, игнорируя поля department
     *
     * @param personDto объект отправки клиенту
     * @return объект приложения
     */
    @Named("fromDtoWithoutDepartment")
    @Mapping(target = "department", ignore = true)
    Person fromDtoWithoutDepartment(PersonDto personDto);
}
