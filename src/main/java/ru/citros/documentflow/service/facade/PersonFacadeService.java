package ru.citros.documentflow.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.mapper.organizational_structure.PersonMapper;
import ru.citros.documentflow.model.organizational_structure.Person;
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
public class PersonFacadeService {

    @Autowired
    private GeneralService<Person> personService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private GeneralRepository<Person> personRepository;

    /**
     * Сохраняет объект
     *
     * @param personDto объект клиента
     * @return объект клиента
     */
    public PersonDto save(PersonDto personDto) {
        Person person = personMapper.fromDto(personDto);
        Person savedPerson = personService.save(person);
        return personMapper.toDto(savedPerson);
    }

    /**
     * Обновляет объект
     *
     * @param personDto объект клиента
     * @return объект клиента
     */
    public PersonDto update(PersonDto personDto) {
        if (personDto.getId() == null) {
            throw new DocumentFlowException("У должности нет id");
        }
        Person person = personMapper.fromDto(personDto);
        Person savedPerson = personService.update(person);
        return personMapper.toDto(savedPerson);
    }

    /**
     * Возвращает список объектов
     *
     * @return список объектов
     */
    public List<PersonDto> getAll() {
        List<Person> people = personRepository.getAll();
        return people.stream().map(personMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Возвращает объект по id
     *
     * @param uuid id
     * @return объект клиента
     */
    public PersonDto getById(UUID uuid) {
        Person person = personRepository.getById(uuid);
        return personMapper.toDto(person);
    }

    /**
     * Удаляет объект по id
     *
     * @param uuid id
     */
    public void delete(UUID uuid) {
        PersonDto personDto = PersonDto.newBuilder().setId(uuid).build();
        Person person = personMapper.fromDto(personDto);
        personRepository.delete(person);
    }
}
