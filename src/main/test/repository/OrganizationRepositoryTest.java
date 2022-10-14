package repository;

import configuration.H2JdbcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository.PostRepository;
import ru.citros.documentflow.repository.organizational_structure.OrganizationRepository;
import ru.citros.documentflow.repository.organizational_structure.PersonRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.PostBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.DepartmentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.OrganizationBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.PersonBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.PostRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.OrganizationRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.PersonRowMapper;
import ru.citros.documentflow.service.converter.ConverterImpl;
import ru.citros.documentflow.service.json.deserialization.JsonDeserializerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        PersonRepository.class,
        H2JdbcConfiguration.class,
        OrganizationRepository.class,
        OrganizationBatchPreparedStatementSetter.class,
        PersonBatchPreparedStatementSetter.class,
        DepartmentBatchPreparedStatementSetter.class,
        OrganizationRowMapper.class,
        PersonRowMapper.class,
        PostRowMapper.class,
        JsonDeserializerImpl.class,
        PostRowMapper.class,
        PostRepository.class,
        PostBatchPreparedStatementSetter.class,
        ConverterImpl.class
})
@ActiveProfiles("test")
public class OrganizationRepositoryTest {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    GeneralRepository<Organization> organizationRepository;

    private List<Person> people;

    private Organization organization;

    @BeforeEach
    public void setUp() {
        people = personRepository.getAll();
    }

    @Test
    public void saveTest() {
        List<String> contactNumbersOrganization = new ArrayList<>();
        contactNumbersOrganization.add("+7 987 025 36 41");
        contactNumbersOrganization.add("+7 963 128 67 12");
        UUID uuidOrganization = UUID.randomUUID();
        Organization organization = Organization.newBuilder()
                .setId(uuidOrganization)
                .setContactPhoneNumbers(contactNumbersOrganization)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .build();
        organizationRepository.save(organization);

        Organization persistsOrganization = organizationRepository.getById(uuidOrganization).get();
        assertEquals(organization.getId(), persistsOrganization.getId());
    }

    @Test
    public void saveAllTest() {
        int currentSizeOrganization = organizationRepository.getAll().size();

        List<String> contactNumbersOrganization = new ArrayList<>();
        contactNumbersOrganization.add("+7 987 025 36 41");
        contactNumbersOrganization.add("+7 963 128 67 12");
        UUID uuidOrganization = UUID.randomUUID();
        Organization organization1 = Organization.newBuilder()
                .setId(uuidOrganization)
                .setContactPhoneNumbers(contactNumbersOrganization)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .build();

        UUID uuidOrganization2 = UUID.randomUUID();
        List<String> contactNumbers2 = new ArrayList<>();
        contactNumbers2.add("+7 333 454 66 55");
        contactNumbers2.add("+7 963 454 68 22");
        Organization organization2 = Organization.newBuilder()
                .setId(uuidOrganization2)
                .setFullName("FBR")
                .setShortName("fbr")
                .setDirector(people.get(0))
                .setContactPhoneNumbers(contactNumbers2)
                .build();


        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);

        organizationRepository.saveAll(organizations);

        int newSizecurrentSizeOrganization = organizationRepository.getAll().size();
        assertEquals(currentSizeOrganization + 2, newSizecurrentSizeOrganization);
    }

    @Test
    public void updateTest() {
        List<Organization> organizations = organizationRepository.getAll();
        Organization currentOrganization = organizations.get(0);
        UUID departmentId = currentOrganization.getId();
        String currentFullName = currentOrganization.getFullName();
        currentOrganization.setFullName("Russian POST");
        organizationRepository.update(currentOrganization);
        Organization updatedOrganization = organizationRepository.getById(departmentId).get();
        String updatedFullName = updatedOrganization.getFullName();
        assertNotEquals(currentFullName, updatedFullName);
    }

    @Test
    public void getAll() {
        List<String> contactNumbersOrganization = new ArrayList<>();
        contactNumbersOrganization.add("+7 987 025 36 41");
        contactNumbersOrganization.add("+7 963 128 67 12");
        UUID uuidOrganization = UUID.randomUUID();
        organization = Organization.newBuilder()
                .setId(uuidOrganization)
                .setContactPhoneNumbers(contactNumbersOrganization)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .build();
        organizationRepository.save(organization);
        int sizeOrganization = organizationRepository.getAll().size();
        assertTrue(sizeOrganization > 0);
    }

    @Test
    public void getByIdTest() {
        List<String> contactNumbersOrganization = new ArrayList<>();
        contactNumbersOrganization.add("+7 987 025 36 41");
        contactNumbersOrganization.add("+7 963 128 67 12");
        UUID uuidOrganization = UUID.randomUUID();
        organization = Organization.newBuilder()
                .setId(uuidOrganization)
                .setContactPhoneNumbers(contactNumbersOrganization)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .build();
        organizationRepository.save(organization);
        Organization savedOrganization = organizationRepository.getById(uuidOrganization).get();
        UUID savedId = savedOrganization.getId();
        assertTrue(uuidOrganization.equals(savedId));
    }

    @Test
    public void deleteTest() {
        List<String> contactNumbersOrganization = new ArrayList<>();
        contactNumbersOrganization.add("+7 987 025 36 41");
        contactNumbersOrganization.add("+7 963 128 67 12");
        UUID uuidOrganization = UUID.randomUUID();
        organization = Organization.newBuilder()
                .setId(uuidOrganization)
                .setContactPhoneNumbers(contactNumbersOrganization)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .build();
        organizationRepository.save(organization);
        int currentSizeOrganization = organizationRepository.getAll().size();
        organizationRepository.delete(organization);
        int sizeOrganizationTableAfterDelete = organizationRepository.getAll().size();
        assertTrue((currentSizeOrganization - sizeOrganizationTableAfterDelete) == 1);
    }
}
