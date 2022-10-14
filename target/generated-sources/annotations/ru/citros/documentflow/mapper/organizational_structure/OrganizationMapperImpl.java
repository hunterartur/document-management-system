package ru.citros.documentflow.mapper.organizational_structure;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.organizational_structure.OrganizationDto;
import ru.citros.documentflow.model.organizational_structure.Organization;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public OrganizationDto toDto(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationDto.Builder organizationDto = OrganizationDto.newBuilder();

        organizationDto.setDirector( personMapper.toDtoWithoutDepartment( organization.getDirector() ) );
        organizationDto.setId( organization.getId() );
        organizationDto.setFullName( organization.getFullName() );
        organizationDto.setShortName( organization.getShortName() );
        List<String> list = organization.getContactPhoneNumbers();
        if ( list != null ) {
            organizationDto.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organizationDto.build();
    }

    @Override
    public OrganizationDto toDtoWithoutDirector(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationDto.Builder organizationDto = OrganizationDto.newBuilder();

        organizationDto.setId( organization.getId() );
        organizationDto.setFullName( organization.getFullName() );
        organizationDto.setShortName( organization.getShortName() );
        List<String> list = organization.getContactPhoneNumbers();
        if ( list != null ) {
            organizationDto.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organizationDto.build();
    }

    @Override
    public Organization fromDto(OrganizationDto organizationDto) {
        if ( organizationDto == null ) {
            return null;
        }

        Organization.Builder organization = Organization.newBuilder();

        organization.setDirector( personMapper.fromDtoWithoutDepartment( organizationDto.getDirector() ) );
        organization.setId( organizationDto.getId() );
        organization.setFullName( organizationDto.getFullName() );
        organization.setShortName( organizationDto.getShortName() );
        List<String> list = organizationDto.getContactPhoneNumbers();
        if ( list != null ) {
            organization.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organization.build();
    }

    @Override
    public Organization fromDtoWithoutDirector(OrganizationDto organizationDto) {
        if ( organizationDto == null ) {
            return null;
        }

        Organization.Builder organization = Organization.newBuilder();

        organization.setId( organizationDto.getId() );
        organization.setFullName( organizationDto.getFullName() );
        organization.setShortName( organizationDto.getShortName() );
        List<String> list = organizationDto.getContactPhoneNumbers();
        if ( list != null ) {
            organization.setContactPhoneNumbers( new ArrayList<String>( list ) );
        }

        return organization.build();
    }
}
