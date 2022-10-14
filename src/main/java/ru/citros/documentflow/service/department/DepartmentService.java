package ru.citros.documentflow.service.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Department;
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
 * Сервис для работы с репозиторием отделов
 *
 * @author AIshmaev
 */
@Service
public class DepartmentService implements GeneralService<Department> {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private GeneralRepository<Department> departmentRepository;

    @Autowired
    private ValidateService<Department> validateService;

    @Autowired
    private GeneralRepository<Organization> organizationRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Department save(Department department) {
        Department validateDepartment = validateDepartment(department);
        return departmentRepository.save(validateDepartment);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Department> saveAll(List<Department> departments) {
        List<Department> validateDepartments = departments.stream().map(this::validateDepartment).collect(Collectors.toList());
        return departmentRepository.saveAll(validateDepartments);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Department update(Department department) {
        Department validateDepartment = validateDepartment(department);
        return departmentRepository.update(validateDepartment);
    }

    /**
     * Производит проверки сущности
     *
     * @param department сущность
     */
    private Department validateDepartment(Department department) {
        String validateString = validateService.validate(department);
        if (!validateString.isEmpty()) {
            throw new DocumentFlowException(MessageFormat.format("Невозможно записать объект в БД", validateString));
        }
        Organization organization = organizationRepository.getById(department.getOrganization().getId());
        Person director = personRepository.getById(department.getDirector().getId());
        department.setOrganization(organization);
        department.setDirector(director);
        return department;
    }
}
