package repository;


import configuration.H2JdbcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository.PostRepository;
import ru.citros.documentflow.repository.organizational_structure.DepartmentRepository;
import ru.citros.documentflow.repository.organizational_structure.OrganizationRepository;
import ru.citros.documentflow.repository.organizational_structure.PersonRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.PostBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.DepartmentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.OrganizationBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.PersonBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.PostRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.DepartmentRowMapper;
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
        DepartmentRepository.class,
        H2JdbcConfiguration.class,
        OrganizationRepository.class,
        DepartmentBatchPreparedStatementSetter.class,
        OrganizationBatchPreparedStatementSetter.class,
        PersonBatchPreparedStatementSetter.class,
        PostBatchPreparedStatementSetter.class,
        OrganizationRowMapper.class,
        DepartmentRowMapper.class,
        PostRowMapper.class,
        PersonRowMapper.class,
        JsonDeserializerImpl.class,
        PostRepository.class,
        ConverterImpl.class
})
@ActiveProfiles("test")
public class DepartmentRepositoryTest {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private GeneralRepository<Department> departmentRepository;

    @Autowired
    private GeneralRepository<Organization> organizationRepository;

    private List<Person> people;

    private List<Organization> organizations;

    @BeforeEach
    public void setUp() {
        people = personRepository.getAll();
        organizations = organizationRepository.getAll();
    }

    @Test
    public void saveTest() {
        UUID uuidDepartment = UUID.randomUUID();
        List<String> contactNumbers = new ArrayList<>();
        contactNumbers.add("+7 987 025 36 41");
        contactNumbers.add("+7 963 128 67 12");

        Department department = Department.newBuilder()
                .setId(uuidDepartment)
                .setFullName("Human hunter")
                .setShortName("HR")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers)
                .build();

        departmentRepository.save(department);

        Department persistsDepartment = departmentRepository.getById(uuidDepartment).get();
        assertEquals(department.getId(), persistsDepartment.getId());
    }

    @Test
    public void saveAllTest() {
        int currentSizeDepartments = departmentRepository.getAll().size();

        UUID uuidDepartment1 = UUID.randomUUID();
        List<String> contactNumbers1 = new ArrayList<>();
        contactNumbers1.add("+7 987 025 36 41");
        contactNumbers1.add("+7 963 128 67 12");
        Department department1 = Department.newBuilder()
                .setId(uuidDepartment1)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers1)
                .build();

        UUID uuidDepartment2 = UUID.randomUUID();
        List<String> contactNumbers2 = new ArrayList<>();
        contactNumbers2.add("+7 333 454 66 55");
        contactNumbers2.add("+7 963 454 68 22");
        Department department2 = Department.newBuilder()
                .setId(uuidDepartment2)
                .setFullName("FBR")
                .setShortName("fbr")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers2)
                .build();


        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);

        departmentRepository.saveAll(departments);

        int newSizeDepartments = departmentRepository.getAll().size();
        assertEquals(currentSizeDepartments + 2, newSizeDepartments);
    }

    @Test
    public void updateTest() {
        List<Department> departments = departmentRepository.getAll();
        Department currentDepartment = departments.get(0);
        UUID departmentId = currentDepartment.getId();
        String currentFullName = currentDepartment.getFullName();
        currentDepartment.setFullName("Russian POST");
        departmentRepository.update(currentDepartment);
        Department updatedDepartment = departmentRepository.getById(departmentId).get();
        String updatedFullName = updatedDepartment.getFullName();
        assertNotEquals(currentFullName, updatedFullName);
    }

    @Test
    public void getAll() {
        UUID uuidDepartment = UUID.randomUUID();
        List<String> contactNumbers = new ArrayList<>();
        contactNumbers.add("+7 987 025 36 41");
        contactNumbers.add("+7 963 128 67 12");
        Department department = Department.newBuilder()
                .setId(uuidDepartment)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers)
                .build();
        departmentRepository.save(department);
        int sizeDepartment = departmentRepository.getAll().size();
        assertTrue(sizeDepartment > 0);
    }

    @Test
    public void getByIdTest() {
        UUID uuidDepartment = UUID.randomUUID();
        List<String> contactNumbers = new ArrayList<>();
        contactNumbers.add("+7 987 025 36 41");
        contactNumbers.add("+7 963 128 67 12");
        Department department = Department.newBuilder()
                .setId(uuidDepartment)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers)
                .build();
        departmentRepository.save(department);
        Department savedDepartment = departmentRepository.getById(uuidDepartment).get();
        UUID savedId = savedDepartment.getId();
        assertTrue(uuidDepartment.equals(savedId));
    }

    @Test
    public void deleteTest() {
        UUID uuidDepartment = UUID.randomUUID();
        List<String> contactNumbers = new ArrayList<>();
        contactNumbers.add("+7 987 025 36 41");
        contactNumbers.add("+7 963 128 67 12");
        Department department = Department.newBuilder()
                .setId(uuidDepartment)
                .setFullName("Citros")
                .setShortName("Citros")
                .setDirector(people.get(0))
                .setOrganization(organizations.get(0))
                .setContactPhoneNumbers(contactNumbers)
                .build();
        departmentRepository.save(department);
        int currentSizeDepartment = departmentRepository.getAll().size();
        departmentRepository.delete(department);
        int sizeDepartmentTableAfterDelete = departmentRepository.getAll().size();
        assertTrue((currentSizeDepartment - sizeDepartmentTableAfterDelete) == 1);
    }
}
