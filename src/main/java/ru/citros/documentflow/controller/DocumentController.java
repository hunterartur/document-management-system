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
import ru.citros.documentflow.dto.document.DocumentDto;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.enumeration.DeliveryMethod;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.service.facade.DocumentFacadeService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Rest контроллер отправки и получения объектов типа DocumentDto
 *
 * @author AIshmaev
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentFacadeService documentFacadeService;

    /**
     * Отправляет список всех объектов клиенту
     *
     * @return список всех объектов клиенту
     */
    @GetMapping("/")
    public ResponseEntity<List<DocumentDto>> getAll() {
        List<DocumentDto> documentDtos = documentFacadeService.getAll();
        return new ResponseEntity<>(documentDtos, HttpStatus.OK);
    }

    /**
     * Отправляет список всех методов доставки клиенту
     *
     * @return список всех методов доставки клиенту
     */
    @GetMapping("/delivery-method")
    public ResponseEntity<List<DeliveryMethod>> getAllDeliveryMethod() {
        List<DeliveryMethod> deliveryMethods = Arrays.stream(DeliveryMethod.values()).collect(Collectors.toList());
        return new ResponseEntity<>(deliveryMethods, HttpStatus.OK);
    }

    /**
     * Отправляет список все типы документа клиенту
     *
     * @return список все типы документа клиенту
     */
    @GetMapping("/document-type")
    public ResponseEntity<List<DocumentType>> getAllDocumentMethod() {
        List<DocumentType> documentTypes = Arrays.stream(DocumentType.values()).collect(Collectors.toList());
        return new ResponseEntity<>(documentTypes, HttpStatus.OK);
    }

    /**
     * отправляет клиенту объект с id
     *
     * @param id id
     * @return объект
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> findById(@PathVariable UUID id) {
        DocumentDto documentDto = documentFacadeService.getById(id);
        return new ResponseEntity<>(documentDto, HttpStatus.OK);
    }

    /**
     * Обновляет объект и возращает клиенту
     *
     * @param documentDto объект для обновления
     * @return обновленный объект
     */
    @PostMapping("/save")
    public ResponseEntity<DocumentDto> save(@RequestBody DocumentWithAllFieldsOfAllDocumentsDto documentDto) {
        DocumentDto savedDocumentDto = documentFacadeService.save(documentDto);
        return new ResponseEntity<>(savedDocumentDto, HttpStatus.CREATED);
    }

    /**
     * Сохраняет объект и возвращает сохраненный объект
     *
     * @param documentDto объект для сохранения
     * @return сохраненный объект
     */
    @PutMapping("/update")
    public ResponseEntity<DocumentDto> update(@RequestBody DocumentWithAllFieldsOfAllDocumentsDto documentDto) {
        DocumentDto updateDocumentDto = documentFacadeService.update(documentDto);
        return new ResponseEntity<>(updateDocumentDto, HttpStatus.OK);
    }

    /**
     * Удаляет объект
     *
     * @param id id
     * @return статус операции
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        documentFacadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}