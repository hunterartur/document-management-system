package ru.citros.documentflow.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.dto.document.DocumentDto;
import ru.citros.documentflow.dto.document.DocumentWithAllFieldsOfAllDocumentsDto;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.mapper.document.ChildDocumentMapper;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.repository.GeneralRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Слой приложения для работы с dto
 *
 * @author AIshmaev
 */
@Service
public class DocumentFacadeService {

    @Autowired
    private GeneralRepository<Document> documentRepository;

    @Autowired
    private List<ChildDocumentMapper> childDocumentMappers;

    /**
     * Сохраняет объект
     *
     * @param documentsDto объект клиента
     * @return объект клиента
     */
    public DocumentDto save(DocumentWithAllFieldsOfAllDocumentsDto documentsDto) {
        DocumentType documentType = documentsDto.getDocumentType();
        if (documentType == null) {
            throw new DocumentFlowException("Не указан тип документа");
        }
        ChildDocumentMapper documentMapper = findChildDocumentMapper(documentType.getName());
        Document document = documentMapper.fromDto(documentsDto);
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDto(savedDocument);
    }

    /**
     * Обновляет объект
     *
     * @param documentsDto объект клиента
     * @return объект клиента
     */
    public DocumentDto update(DocumentWithAllFieldsOfAllDocumentsDto documentsDto) {
        DocumentType documentType = documentsDto.getDocumentType();
        if (documentsDto.getId() == null) {
            throw new DocumentFlowException("У должности нет id");
        }
        if (documentType == null) {
            throw new DocumentFlowException("Не указан тип документа");
        }
        ChildDocumentMapper documentMapper = findChildDocumentMapper(documentType.getName());
        Document document = documentMapper.fromDto(documentsDto);
        Document updatedDocument = documentRepository.update(document);
        return documentMapper.toDto(updatedDocument);
    }

    /**
     * Возвращает список объектов
     *
     * @return список объектов
     */
    public List<DocumentDto> getAll() {
        List<Document> documents = documentRepository.getAll();
        return documents.stream()
                .map(document -> findChildDocumentMapper(document.getClass().getSimpleName()).toDto(document)).collect(Collectors.toList());
    }

    /**
     * Возвращает объект по id
     *
     * @param uuid id
     * @return объект клиента
     */
    public DocumentDto getById(UUID uuid) {
        Document document = documentRepository.getById(uuid);
        ChildDocumentMapper documentMapper = findChildDocumentMapper(document.getClass().getSimpleName());
        return documentMapper.toDto(document);
    }

    /**
     * Удаляет объект по id
     *
     * @param uuid id
     */
    public void delete(UUID uuid) {
        Document document = documentRepository.getById(uuid);
        documentRepository.delete(document);
    }

    /**
     * Ищет дочерний маппер по имени типа документа или класса
     *
     * @param nameDocumentTypeOrNameClass имя типа документа или класса
     * @return дочерний маппер
     */
    private ChildDocumentMapper findChildDocumentMapper(String nameDocumentTypeOrNameClass) {
        return childDocumentMappers.stream()
                .filter(childDocumentMapper -> nameDocumentTypeOrNameClass.contains(childDocumentMapper.getName()))
                .findAny()
                .orElseThrow(() -> new DocumentFlowException(MessageFormat.format("Нет маппера для типа - {0} документа",
                        nameDocumentTypeOrNameClass)));
    }
}
