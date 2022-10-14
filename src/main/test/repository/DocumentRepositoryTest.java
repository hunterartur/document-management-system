package repository;

import configuration.H2JdbcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import ru.citros.documentflow.configuration.CommonConfigurationProperties;
import ru.citros.documentflow.creator.DocumentCreatorImpl;
import ru.citros.documentflow.factory.IncomingDocumentFactory;
import ru.citros.documentflow.factory.OutgoingDocumentFactory;
import ru.citros.documentflow.factory.TaskDocumentFactory;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository.document.DocumentRepository;
import ru.citros.documentflow.repository.document.IncomingDocumentRepository;
import ru.citros.documentflow.repository.document.OutgoingDocumentRepository;
import ru.citros.documentflow.repository.document.TaskDocumentRepository;
import ru.citros.documentflow.repository.organizational_structure.PersonRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.document.DocumentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.document.IncomingDocumentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.document.OutgoingDocumentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.document.TaskDocumentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.PersonBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.document.DocumentRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.document.IncomingDocumentRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.document.OutgoingDocumentRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.document.TaskDocumentRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.PersonRowMapper;
import ru.citros.documentflow.service.converter.ConverterImpl;
import ru.citros.documentflow.service.generation.DocumentGenerationService;
import ru.citros.documentflow.service.generation.DocumentGenerationServiceImpl;
import ru.citros.documentflow.service.random.RandomServiceImpl;
import ru.citros.documentflow.service.validate.ValidateServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        PersonRepository.class,
        H2JdbcConfiguration.class,
        RowMapper.class,
        DocumentRepository.class,
        OutgoingDocumentRepository.class,
        IncomingDocumentRepository.class,
        TaskDocumentRepository.class,
        DocumentRowMapper.class,
        IncomingDocumentRowMapper.class,
        OutgoingDocumentRowMapper.class,
        TaskDocumentRowMapper.class,
        DocumentGenerationServiceImpl.class,
        ValidateServiceImpl.class,
        RandomServiceImpl.class,
        DocumentCreatorImpl.class,
        IncomingDocumentFactory.class,
        OutgoingDocumentFactory.class,
        TaskDocumentFactory.class,
        CommonConfigurationProperties.class,
        DocumentBatchPreparedStatementSetter.class,
        PersonBatchPreparedStatementSetter.class,
        TaskDocumentBatchPreparedStatementSetter.class,
        OutgoingDocumentBatchPreparedStatementSetter.class,
        IncomingDocumentBatchPreparedStatementSetter.class,
        PersonRowMapper.class,
        ConverterImpl.class
})
@ActiveProfiles("test")
public class DocumentRepositoryTest {

    @Autowired
    private DocumentGenerationService documentGenerationService;

    @Autowired
    private CommonConfigurationProperties properties;

    @Autowired
    private GeneralRepository<Document> documentRepository;

    @BeforeEach
    void setUp() {
        properties.setDateInterval(10965);
        properties.setLengthGeneratedString(10);
        properties.setCountDocuments(10);
    }

    @Test
    public void saveTest() {
        Document document = documentGenerationService.generateDocuments(properties.getCountDocuments()).get(0);
        documentRepository.save(document);
        Document savedDocument = documentRepository.getById(document.getId()).get();
        assertEquals(document.getId().toString(), savedDocument.getId().toString());
    }

    @Test
    public void saveAllTest() {
        List<Document> documents = documentGenerationService.generateDocuments(properties.getCountDocuments());
        int currentSizeDocuments = documentRepository.getAll().size();
        documentRepository.saveAll(documents);
        int newSizeDocuments = documentRepository.getAll().size();
        assertTrue((newSizeDocuments - currentSizeDocuments) > 0);

    }

    @Test
    public void updateTest() {
        Document document = documentGenerationService.generateDocuments(properties.getCountDocuments()).get(0);
        documentRepository.save(document);
        UUID idDocument = document.getId();
        document.setName("Test Document");
        documentRepository.update(document);
        Document savedDocument = documentRepository.getById(idDocument).get();
        assertEquals(document.getName(), savedDocument.getName());
    }

    @Test
    public void getAll() {
        int currentSizeDocument = documentRepository.getAll().size();
        List<Document> documents = documentGenerationService.generateDocuments(properties.getCountDocuments());
        documentRepository.saveAll(documents);
        int newSizeDocument = documentRepository.getAll().size();
        assertTrue((newSizeDocument - currentSizeDocument) > 0);
    }

    @Test
    public void getByIdTest() {
        Document document = documentGenerationService.generateDocuments(properties.getCountDocuments()).get(0);
        documentRepository.save(document);
        UUID idDocument = document.getId();
        document.setName("Test Document");
        documentRepository.update(document);
        Document savedDocument = documentRepository.getById(idDocument).get();
        assertEquals(idDocument.toString(), savedDocument.getId().toString());
    }

    @Test
    public void deleteTest() {
        Document document = documentGenerationService.generateDocuments(properties.getCountDocuments()).get(0);
        documentRepository.save(document);
        int currentSizeDocuments = documentRepository.getAll().size();
        documentRepository.delete(document);
        int newSizeDocuments = documentRepository.getAll().size();
        assertTrue((currentSizeDocuments - newSizeDocuments) == 1);
    }
}
