package ru.citros.documentflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.citros.documentflow.dto.organizational_structure.OrganizationDto;
import ru.citros.documentflow.service.facade.OrganizationFacadeService;

import java.util.List;
import java.util.UUID;

/**
 * Rest контроллер отправки и получения объектов типа OrganizationDto
 *
 * @author AIshmaev
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationFacadeService organizationFacadeService;

    /**
     * Отправляет список всех объектов клиенту
     *
     * @return список всех объектов клиенту
     */
    @GetMapping("/")
    public ResponseEntity<List<OrganizationDto>> getAll() {
        List<OrganizationDto> organizationDtos = organizationFacadeService.getAll();
        return new ResponseEntity<>(organizationDtos, HttpStatus.OK);
    }

    /**
     * отправляет клиенту объект с id
     *
     * @param id id
     * @return объект
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> findById(@PathVariable UUID id) {
        OrganizationDto organizationDto = organizationFacadeService.getById(id);
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    /**
     * Обновляет объект и возращает клиенту
     *
     * @param organizationDto объект для обновления
     * @return обновленный объект
     */
    @PutMapping("/update")
    public ResponseEntity<OrganizationDto> update(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto updateOrganizationDto = organizationFacadeService.update(organizationDto);
        return new ResponseEntity<>(updateOrganizationDto, HttpStatus.OK);
    }

    /**
     * Сохраняет объект и возвращает сохраненный объект
     *
     * @param organizationDto объект для сохранения
     * @return сохраненный объект
     */
    @PostMapping("/save")
    public ResponseEntity<OrganizationDto> save(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto savedOrganizationDto = organizationFacadeService.save(organizationDto);
        return new ResponseEntity<>(savedOrganizationDto, HttpStatus.CREATED);
    }

    /**
     * Удаляет объект
     *
     * @param id id
     * @return статус операции
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        organizationFacadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
