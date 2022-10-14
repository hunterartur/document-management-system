package ru.citros.documentflow.service.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.GeneralService;
import ru.citros.documentflow.service.validate.ValidateService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с репозиторием организации
 *
 * @author AIshmaev
 */
@Service
public class OrganizationService implements GeneralService<Organization> {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private GeneralRepository<Organization> organizationRepository;

    @Autowired
    private ValidateService<Organization> validateService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Organization save(Organization organization) {
        Organization validateOrganization = validateOrganization(organization);
        return organizationRepository.save(validateOrganization);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Organization> saveAll(List<Organization> organizations) {
        List<Organization> validateOrganizations = organizations.stream().map(this::validateOrganization).collect(Collectors.toList());
        return organizationRepository.saveAll(validateOrganizations);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Organization update(Organization organization) {
        Organization validateOrganization = validateOrganization(organization);
        return organizationRepository.update(validateOrganization);
    }

    /**
     * Производит проверки сущности
     *
     * @param organization сущность
     * @return проверенный объект
     */
    private Organization validateOrganization(Organization organization) {
        String validateString = validateService.validate(organization);
        if (!validateString.isEmpty()) {
            throw new DocumentFlowException(MessageFormat.format("Невозможно записать объект в БД", validateString));
        }
        Person director = personRepository.getById(organization.getDirector().getId());
        organization.setDirector(director);
        return organization;
    }
}
