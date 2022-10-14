package repository;

import configuration.H2JdbcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository.PostRepository;
import ru.citros.documentflow.repository.organizational_structure.DepartmentRepository;
import ru.citros.documentflow.repository.organizational_structure.PersonRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.PostBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.DepartmentBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.preparedstatementsetter.organizational_structure.PersonBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.PostRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.DepartmentRowMapper;
import ru.citros.documentflow.repository_util.row_mapper.organizational_structure.PersonRowMapper;
import ru.citros.documentflow.service.converter.ConverterImpl;
import ru.citros.documentflow.service.json.deserialization.JsonDeserializerImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        PersonRepository.class,
        PostRepository.class,
        DepartmentRepository.class,
        H2JdbcConfiguration.class,
        PersonBatchPreparedStatementSetter.class,
        PostBatchPreparedStatementSetter.class,
        DepartmentBatchPreparedStatementSetter.class,
        DepartmentRowMapper.class,
        DepartmentBatchPreparedStatementSetter.class,
        PersonRowMapper.class,
        PostRowMapper.class,
        JsonDeserializerImpl.class,
        JsonDeserializerImpl.class,
        ConverterImpl.class
})
@ActiveProfiles("test")
public class PersonRepositoryTest {

    @Autowired
    private GeneralRepository<Person> personRepository;

    @Autowired
    private GeneralRepository<Post> postRepository;

    @Autowired
    private GeneralRepository<Department> departmentRepository;

    private List<Post> posts;

    private List<Department> departments;

    @BeforeEach
    public void setUp() {
        posts = postRepository.getAll();
        departments = departmentRepository.getAll();
    }

    @Test
    public void saveTest() {
        UUID uuidPerson = UUID.randomUUID();
        Person person = Person.newBuilder()
                .setId(uuidPerson)
                .setFirstName("Artur")
                .setLastName("Ishmaev")
                .setPatronymic("Ilgizovich")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1994, 1, 15))
                .build();
        personRepository.save(person);
        Person persistsPerson = personRepository.getById(uuidPerson).get();
        assertEquals(person.getId(), persistsPerson.getId());
    }

    @Test
    public void saveAllTest() {
        int currentSizePeople = personRepository.getAll().size();

        UUID uuidPerson1 = UUID.randomUUID();
        Person person1 = Person.newBuilder()
                .setId(uuidPerson1)
                .setFirstName("Artur")
                .setLastName("Ishmaev")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1994, 1, 15))
                .setPatronymic("Ilgizovich")
                .build();

        UUID uuidPerson2 = UUID.randomUUID();
        Person person2 = Person.newBuilder()
                .setId(uuidPerson2)
                .setFirstName("Anton")
                .setLastName("Petrov")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1991, 5, 19))
                .setPatronymic("Ivanovich")
                .build();

        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);

        personRepository.saveAll(people);

        int newSizePeople = personRepository.getAll().size();
        assertEquals(currentSizePeople + 2, newSizePeople);
    }

    @Test
    public void updateTest() {
        List<Person> people = personRepository.getAll();
        Person currentPerson = people.get(0);
        UUID personId = currentPerson.getId();
        String currentName = currentPerson.getLastName();
        currentPerson.setLastName("Vadzukian");
        personRepository.update(currentPerson);
        Person updatedPerson = personRepository.getById(personId).get();
        String updatedName = updatedPerson.getLastName();
        assertNotEquals(currentName, updatedName);
    }

    @Test
    public void getAll() {
        UUID uuidPerson = UUID.randomUUID();
        Person person = Person.newBuilder()
                .setId(uuidPerson)
                .setFirstName("Anton")
                .setLastName("Petrov")
                .setPatronymic("Ivanovich")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1991, 5, 19))
                .build();
        personRepository.save(person);
        int sizePerson = personRepository.getAll().size();
        assertTrue(sizePerson > 0);
    }

    @Test
    public void getByIdTest() {
        UUID uuidPerson = UUID.randomUUID();
        Person person = Person.newBuilder()
                .setId(uuidPerson)
                .setFirstName("Anton")
                .setLastName("Petrov")
                .setPatronymic("Ivanovich")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1994, 1, 15))
                .build();
        personRepository.save(person);
        Person savedPerson = personRepository.getById(uuidPerson).get();
        UUID savedId = savedPerson.getId();
        assertTrue(uuidPerson.equals(savedId));
    }

    @Test
    public void deleteTest() {
        UUID uuidPerson = UUID.randomUUID();
        Person person = Person.newBuilder()
                .setId(uuidPerson)
                .setFirstName("Anton")
                .setLastName("Petrov")
                .setPatronymic("Ivanovich")
                .setPost(posts.get(0))
                .setDepartment(departments.get(0))
                .setBirthday(LocalDate.of(1994, 1, 15))
                .build();
        personRepository.save(person);
        int currentSizePeople = personRepository.getAll().size();
        personRepository.delete(person);
        int sizePeopleTableAfterDelete = personRepository.getAll().size();
        assertTrue((currentSizePeople - sizePeopleTableAfterDelete) == 1);
    }
}
