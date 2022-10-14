package ru.citros.documentflow.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.GeneralService;
import ru.citros.documentflow.service.validate.ValidateService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с репозиторием сотрудников
 *
 * @author AIshmaev
 */
@Service
public class PersonService implements GeneralService<Person> {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private GeneralRepository<Post> postRepository;

    @Autowired
    private ValidateService<Person> validateService;

    @Autowired
    private GeneralRepository<Department> departmentRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Person save(Person person) {
        Person validatePerson = validatePerson(person);
        return personRepository.save(validatePerson);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Person> saveAll(List<Person> people) {
        List<Person> validatePeople = people.stream().map(this::validatePerson).collect(Collectors.toList());
        return personRepository.saveAll(validatePeople);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Person update(Person person) {
        Person validatePerson = validatePerson(person);
        return personRepository.update(validatePerson);
    }

    /**
     * Производит проверки сущности
     *
     * @param person сущность
     * @return проверенный объект
     */
    private Person validatePerson(Person person) {
        String validateString = validateService.validate(person);
        if (!validateString.isEmpty()) {
            throw new DocumentFlowException(MessageFormat.format("Невозможно записать объект в БД", validateString));
        }
        Post post = postRepository.getById(person.getPost().getId());
        Department department = departmentRepository.getById(person.getDepartment().getId());
        person.setPost(post);
        person.setDepartment(department);
        return person;
    }
}
