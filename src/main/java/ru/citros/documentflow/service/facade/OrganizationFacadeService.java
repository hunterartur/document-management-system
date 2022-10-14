package ru.citros.documentflow.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.dto.organizational_structure.OrganizationDto;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.mapper.organizational_structure.OrganizationMapper;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.GeneralService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Слой приложения для работы с dto
 *
 * @author AIshmaev
 */
@Service
public class OrganizationFacadeService {

    @Autowired
    private GeneralService<Organization> organizationService;

    @Autowired
    private GeneralRepository<Organization> organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * Сохраняет объект
     *
     * @param organizationDto объект клиента
     * @return объект клиента
     */
    public OrganizationDto save(OrganizationDto organizationDto) {
        Organization organization = organizationMapper.fromDto(organizationDto);
        Organization savedOrganization = organizationService.save(organization);
        return organizationMapper.toDto(savedOrganization);
    }

    /**
     * Обновляет объект
     *
     * @param organizationDto объект клиента
     * @return объект клиента
     */
    public OrganizationDto update(OrganizationDto organizationDto) {
        if (organizationDto.getId() == null) {
            throw new DocumentFlowException("У должности нет id");
        }
        Organization organization = organizationMapper.fromDto(organizationDto);
        Organization savedOrganization = organizationService.update(organization);
        return organizationMapper.toDto(savedOrganization);
    }

    /**
     * Возвращает список объектов
     *
     * @return список объектов
     */
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = organizationRepository.getAll();
        return organizations.stream().map(organizationMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Возвращает объект по id
     *
     * @param uuid id
     * @return объект клиента
     */
    public OrganizationDto getById(UUID uuid) {
        Organization organization = organizationRepository.getById(uuid);
        return organizationMapper.toDto(organization);
    }

    /**
     * Удаляет объект по id
     *
     * @param uuid id
     */
    public void delete(UUID uuid) {
        OrganizationDto organizationDto = OrganizationDto.newBuilder().setId(uuid).build();
        Organization organization = organizationMapper.fromDto(organizationDto);
        organizationRepository.delete(organization);
    }
}
