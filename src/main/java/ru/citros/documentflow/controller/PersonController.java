package ru.citros.documentflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.service.facade.PersonFacadeService;

import java.util.List;
import java.util.UUID;

/**
 * Rest контроллер отправки и получения объектов типа PersonDto
 *
 * @author AIshmaev
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonFacadeService personFacadeService;

    /**
     * Отправляет список всех объектов клиенту
     *
     * @return список всех объектов клиенту
     */
    @GetMapping("/")
    public ResponseEntity<List<PersonDto>> getAll() {
        List<PersonDto> personDtos = personFacadeService.getAll();
        return new ResponseEntity<>(personDtos, HttpStatus.OK);
    }

    /**
     * отправляет клиенту объект с id
     *
     * @param id id
     * @return объект
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable UUID id) {
        PersonDto personDto = personFacadeService.getById(id);
        return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    /**
     * Обновляет объект и возращает клиенту
     *
     * @param personDto объект для обновления
     * @return обновленный объект
     */
    @PutMapping("/update")
    public ResponseEntity<PersonDto> update(@RequestBody PersonDto personDto) {
        PersonDto updatePersonDto = personFacadeService.update(personDto);
        return new ResponseEntity<>(updatePersonDto, HttpStatus.OK);
    }

    /**
     * Сохраняет объект и возвращает сохраненный объект
     *
     * @param personDto объект для сохранения
     * @return сохраненный объект
     */
    @PostMapping("/save")
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto) {
        PersonDto savedPersonDto = personFacadeService.save(personDto);
        return new ResponseEntity<>(savedPersonDto, HttpStatus.CREATED);
    }

    /**
     * Удаляет объект
     *
     * @param id id
     * @return статус операции
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        personFacadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
