package ru.citros.documentflow.mapper.organizational_structure;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.model.organizational_structure.Person;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:18+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public PersonDto toDto(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto.Builder personDto = PersonDto.newBuilder();

        personDto.setDepartment( departmentMapper.toDtoWithoutDirectorAndOrganization( person.getDepartment() ) );
        personDto.setId( person.getId() );
        personDto.setFirstName( person.getFirstName() );
        personDto.setLastName( person.getLastName() );
        personDto.setPatronymic( person.getPatronymic() );
        personDto.setPost( postMapper.toDto( person.getPost() ) );
        personDto.setPhoto( person.getPhoto() );
        personDto.setBirthday( person.getBirthday() );
        personDto.setPhoneNumber( person.getPhoneNumber() );

        return personDto.build();
    }

    @Override
    public PersonDto toDtoWithoutDepartment(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto.Builder personDto = PersonDto.newBuilder();

        personDto.setId( person.getId() );
        personDto.setFirstName( person.getFirstName() );
        personDto.setLastName( person.getLastName() );
        personDto.setPatronymic( person.getPatronymic() );
        personDto.setPost( postMapper.toDto( person.getPost() ) );
        personDto.setPhoto( person.getPhoto() );
        personDto.setBirthday( person.getBirthday() );
        personDto.setPhoneNumber( person.getPhoneNumber() );

        return personDto.build();
    }

    @Override
    public Person fromDto(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person.Builder person = Person.newBuilder();

        person.setDepartment( departmentMapper.fromDtoWithoutDirectorAndOrganization( personDto.getDepartment() ) );
        person.setId( personDto.getId() );
        person.setFirstName( personDto.getFirstName() );
        person.setLastName( personDto.getLastName() );
        person.setPatronymic( personDto.getPatronymic() );
        person.setPost( postMapper.fromDto( personDto.getPost() ) );
        person.setPhoto( personDto.getPhoto() );
        person.setBirthday( personDto.getBirthday() );
        person.setPhoneNumber( personDto.getPhoneNumber() );

        return person.build();
    }

    @Override
    public Person fromDtoWithoutDepartment(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person.Builder person = Person.newBuilder();

        person.setId( personDto.getId() );
        person.setFirstName( personDto.getFirstName() );
        person.setLastName( personDto.getLastName() );
        person.setPatronymic( personDto.getPatronymic() );
        person.setPost( postMapper.fromDto( personDto.getPost() ) );
        person.setPhoto( personDto.getPhoto() );
        person.setBirthday( personDto.getBirthday() );
        person.setPhoneNumber( personDto.getPhoneNumber() );

        return person.build();
    }
}
