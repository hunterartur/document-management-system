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
import ru.citros.documentflow.dto.organizational_structure.PostDto;
import ru.citros.documentflow.service.facade.PostFacadeService;

import java.util.List;
import java.util.UUID;

/**
 * Rest контроллер отправки и получения объектов типа PostDto
 *
 * @author AIshmaev
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostFacadeService postFacadeService;

    /**
     * Отправляет список всех объектов клиенту
     *
     * @return список всех объектов клиенту
     */
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAll() {
        List<PostDto> postDtos = postFacadeService.getAll();
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    /**
     * отправляет клиенту объект с id
     *
     * @param id id
     * @return объект
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable UUID id) {
        PostDto postDto = postFacadeService.getById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    /**
     * Обновляет объект и возращает клиенту
     *
     * @param postDto объект для обновления
     * @return обновленный объект
     */
    @PutMapping("/update")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto) {
        PostDto updatePostDto = postFacadeService.update(postDto);
        return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
    }

    /**
     * Сохраняет объект и возвращает сохраненный объект
     *
     * @param postDto объект для сохранения
     * @return сохраненный объект
     */
    @PostMapping("/save")
    public ResponseEntity<PostDto> save(@RequestBody PostDto postDto) {
        PostDto savedPostDto = postFacadeService.save(postDto);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    /**
     * Удаляет объект
     *
     * @param id id
     * @return статус операции
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        postFacadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
