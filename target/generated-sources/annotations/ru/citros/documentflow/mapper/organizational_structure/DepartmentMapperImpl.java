package ru.citros.documentflow.mapper.organizational_structure;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.organizational_structure.DepartmentDto;
import ru.citros.documentflow.dto.organizational_structure.OrganizationDto;
import ru.citros.documentflow.dto.organizational_structure.PersonDto;
import ru.citros.documentflow.dto.organizational_structure.PostDto;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.model.organizational_structure.Organization;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.model.organizational_structure.Post;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public DepartmentDto toDto(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDto.Builder departmentDto = DepartmentDto.newBuilder();

        departmentDto.setId( department.getId() );
        departmentDto.setFullName( department.getFullName() );
        departmentDto.setShortName( department.getShortName() );
        departmentDto.setDirector( personToPersonDto( department.getDirector() ) );
        List<String> list = department.getContactPhoneNumbers();
        if ( list != null ) {
            departmentDto.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }
        departmentDto.setOrganization( organizationToOrganizationDto( department.getOrganization() ) );

        return departmentDto.build();
    }

    @Override
    public DepartmentDto toDtoWithoutDirectorAndOrganization(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDto.Builder departmentDto = DepartmentDto.newBuilder();

        departmentDto.setId( department.getId() );
        departmentDto.setFullName( department.getFullName() );
        departmentDto.setShortName( department.getShortName() );
        List<String> list = department.getContactPhoneNumbers();
        if ( list != null ) {
            departmentDto.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return departmentDto.build();
    }

    @Override
    public Department fromDto(DepartmentDto departmentDto) {
        if ( departmentDto == null ) {
            return null;
        }

        Department.Builder department = Department.newBuilder();

        department.setId( departmentDto.getId() );
        department.setFullName( departmentDto.getFullName() );
        department.setShortName( departmentDto.getShortName() );
        department.setDirector( personDtoToPerson( departmentDto.getDirector() ) );
        List<String> list = departmentDto.getContactPhoneNumbers();
        if ( list != null ) {
            department.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }
        department.setOrganization( organizationDtoToOrganization( departmentDto.getOrganization() ) );

        return department.build();
    }

    @Override
    public Department fromDtoWithoutDirectorAndOrganization(DepartmentDto departmentDto) {
        if ( departmentDto == null ) {
            return null;
        }

        Department.Builder department = Department.newBuilder();

        department.setId( departmentDto.getId() );
        department.setFullName( departmentDto.getFullName() );
        department.setShortName( departmentDto.getShortName() );
        List<String> list = departmentDto.getContactPhoneNumbers();
        if ( list != null ) {
            department.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return department.build();
    }

    protected PostDto postToPostDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( post.getId() );
        postDto.setName( post.getName() );

        return postDto;
    }

    protected PersonDto personToPersonDto(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto.Builder personDto = PersonDto.newBuilder();

        personDto.setId( person.getId() );
        personDto.setFirstName( person.getFirstName() );
        personDto.setLastName( person.getLastName() );
        personDto.setPatronymic( person.getPatronymic() );
        personDto.setPost( postToPostDto( person.getPost() ) );
        personDto.setPhoto( person.getPhoto() );
        personDto.setBirthday( person.getBirthday() );
        personDto.setPhoneNumber( person.getPhoneNumber() );
        personDto.setDepartment( toDto( person.getDepartment() ) );

        return personDto.build();
    }

    protected OrganizationDto organizationToOrganizationDto(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationDto.Builder organizationDto = OrganizationDto.newBuilder();

        organizationDto.setId( organization.getId() );
        organizationDto.setFullName( organization.getFullName() );
        organizationDto.setShortName( organization.getShortName() );
        organizationDto.setDirector( personToPersonDto( organization.getDirector() ) );
        List<String> list = organization.getContactPhoneNumbers();
        if ( list != null ) {
            organizationDto.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organizationDto.build();
    }

    protected Post postDtoToPost(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDto.getId() );
        post.setName( postDto.getName() );

        return post;
    }

    protected Person personDtoToPerson(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person.Builder person = Person.newBuilder();

        person.setId( personDto.getId() );
        person.setFirstName( personDto.getFirstName() );
        person.setLastName( personDto.getLastName() );
        person.setPatronymic( personDto.getPatronymic() );
        person.setPost( postDtoToPost( personDto.getPost() ) );
        person.setPhoto( personDto.getPhoto() );
        person.setBirthday( personDto.getBirthday() );
        person.setPhoneNumber( personDto.getPhoneNumber() );
        person.setDepartment( fromDto( personDto.getDepartment() ) );

        return person.build();
    }

    protected Organization organizationDtoToOrganization(OrganizationDto organizationDto) {
        if ( organizationDto == null ) {
            return null;
        }

        Organization.Builder organization = Organization.newBuilder();

        organization.setId( organizationDto.getId() );
        organization.setFullName( organizationDto.getFullName() );
        organization.setShortName( organizationDto.getShortName() );
        organization.setDirector( personDtoToPerson( organizationDto.getDirector() ) );
        List<String> list = organizationDto.getContactPhoneNumbers();
        if ( list != null ) {
            organization.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organization.build();
    }
}
