package ru.citros.documentflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.citros.documentflow.configuration.CommonConfigurationProperties;
import ru.citros.documentflow.configuration.DatabaseConfigurationProperties;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.generation.DocumentGenerationService;
import ru.citros.documentflow.service.report.ReportService;
import ru.citros.documentflow.service.xml_util.XmlUnmarshallerService;

import java.io.InputStream;
import java.util.List;

/**
 * Точка входа в программу.
 *
 * @author AIshmaev
 */
@SpringBootApplication
@EnableConfigurationProperties({CommonConfigurationProperties.class, DatabaseConfigurationProperties.class})
public class DocumentFlowApplication implements CommandLineRunner {

    @Autowired
    private CommonConfigurationProperties properties;

    @Autowired
    private GeneralRepository<Department> departmentRepository;
    @Autowired
    private GeneralRepository<Organization> organizationRepository;

    @Autowired
    private GeneralRepository<Post> postRepository;

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private XmlUnmarshallerService<Person> personXmlUnmarshallerService;

    @Autowired
    private XmlUnmarshallerService<Department> departmentXmlUnmarshallerService;

    @Autowired
    private XmlUnmarshallerService<Organization> organizationXmlUnmarshallerService;

    @Autowired
    private GeneralRepository<Document> documentRepository;

    @Autowired
    private DocumentGenerationService documentGenerationService;

    @Autowired
    private ReportService<Document> documentReportService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(DocumentFlowApplication.class, args);
    }

    /**
     * Запуск консольного приложения
     *
     * @param args аргументы командной строки
     */
    @Override
    public void run(String... args) {
        String pathToPersonFile = properties.getPathToPersonXmlFile();
        String pathToDepartmentXmlFile = properties.getPathToDepartmentXmlFile();
        String pathToOrganizationXmlFile = properties.getPathToOrganizationXmlFile();

        String pathToJsonFiles = properties.getPathToJsonDirectory();
        int countDocuments = properties.getCountDocuments();

        InputStream resourceAsStreamOrganization = this.getClass().getClassLoader().getResourceAsStream(pathToOrganizationXmlFile);
        InputStream resourceAsStreamDepartment = this.getClass().getClassLoader().getResourceAsStream(pathToDepartmentXmlFile);
        InputStream resourceAsStreamPerson = this.getClass().getClassLoader().getResourceAsStream(pathToPersonFile);

        List<Organization> organizations = organizationXmlUnmarshallerService.parse(resourceAsStreamOrganization);
        List<Department> departments = departmentXmlUnmarshallerService.parse(resourceAsStreamDepartment);
        List<Person> people = personXmlUnmarshallerService.parse(resourceAsStreamPerson);

        try {
            fillStaffDb(organizations, departments, people);
        } catch (Exception e) {
            logger.info("База данных уже содержит эти записи!", e);
        }

        List<Document> documents = documentGenerationService.generateDocuments(countDocuments);
        List<Document> savedDocuments = documentRepository.saveAll(documents);
        documentReportService.generateReport(savedDocuments, pathToJsonFiles);
    }

    /**
     * Заполняет БД данными оргструктур
     *
     * @param organizations организация
     * @param departments   отделы
     * @param people        сотрудники
     */
    private void fillStaffDb(List<Organization> organizations, List<Department> departments, List<Person> people) {

        //Заполняем организацию без директора, так как его пока нет в БД
        for (Organization organization : organizations) {
            Person director = organization.getDirector();
            organization.setDirector(null);
            organizationRepository.save(organization);
            organization.setDirector(director);
        }

        //Записываем отделы без директора, так как его пока нет в БД
        for (Department department : departments) {
            Person director = department.getDirector();
            department.setDirector(null);
            departmentRepository.save(department);
            department.setDirector(director);
        }

        //Записываем сотрудников в БД
        for (Person person : people) {
            Post post = person.getPost();
            postRepository.save(post);
            personRepository.save(person);
        }

        //Обновляем записи организации, добавляем директора
        for (Organization organization : organizations) {
            organizationRepository.update(organization);
        }

        //Обновляем записи отделов, добавляем директора
        for (Department department : departments) {
            departmentRepository.update(department);
        }
    }
}
