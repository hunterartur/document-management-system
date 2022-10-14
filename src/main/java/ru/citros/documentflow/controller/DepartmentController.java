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
import ru.citros.documentflow.dto.organizational_structure.DepartmentDto;
import ru.citros.documentflow.service.facade.DepartmentFacadeService;

import java.util.List;
import java.util.UUID;

/**
 * Rest контроллер отправки и получения объектов типа DepartmentDto
 *
 * @author AIshmaev
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentFacadeService departmentFacadeService;

    /**
     * Отправляет список всех объектов клиенту
     *
     * @return список всех объектов клиенту
     */
    @GetMapping("/")
    public ResponseEntity<List<DepartmentDto>> getAll() {
        List<DepartmentDto> departmentDtos = departmentFacadeService.getAll();
        return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }

    /**
     * отправляет клиенту объект с id
     *
     * @param id id
     * @return объект
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable UUID id) {
        DepartmentDto departmentDto = departmentFacadeService.getById(id);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    /**
     * Обновляет объект и возращает клиенту
     *
     * @param departmentDto объект для обновления
     * @return обновленный объект
     */
    @PutMapping("/update")
    public ResponseEntity<DepartmentDto> update(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto updateDepartmentDto = departmentFacadeService.update(departmentDto);
        return new ResponseEntity<>(updateDepartmentDto, HttpStatus.OK);
    }

    /**
     * Сохраняет объект и возвращает сохраненный объект
     *
     * @param departmentDto объект для сохранения
     * @return сохраненный объект
     */
    @PostMapping("/save")
    public ResponseEntity<DepartmentDto> save(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartmentDto = departmentFacadeService.save(departmentDto);
        return new ResponseEntity<>(savedDepartmentDto, HttpStatus.CREATED);
    }

    /**
     * Удаляет объект
     *
     * @param id id
     * @return статус операции
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        departmentFacadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
