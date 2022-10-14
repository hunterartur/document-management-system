package ru.citros.documentflow.repository_util.row_mapper.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.enumeration.DocumentType;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.service.converter.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Связывает поля записи из БД с объектом
 *
 * @author AIshmaev
 */
@Component
@Primary
public class DocumentRowMapper implements RowMapper<Document> {

    @Autowired
    private List<ChildRowMapper> rowMappers;

    @Autowired
    private Converter converter;

    @Override
    public Document mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        String documentTypeFromResultSet = resultSet.getString("document_type");
        DocumentType documentType = Arrays.stream(DocumentType.values())
                .filter(type -> documentTypeFromResultSet.equals(type.getName()))
                .findFirst()
                .orElseThrow(() -> new DocumentFlowException("Неверный тип документа"));
        Document document = rowMappers.stream()
                .filter(childRowMapper -> childRowMapper.getName().equals(documentType.getName()))
                .findFirst()
                .orElseThrow(() -> new DocumentFlowException(MessageFormat.format("Нет маппера для для документа типа документа={0}", documentType)))
                .mapRow(resultSet, rowNum);
        Person author = Person.newBuilder()
                .setId(UUID.fromString(resultSet.getString("author")))
                .build();
        LocalDate registrationDate = converter.convertDateToLocalDate(resultSet.getDate("registration_date"));
        document.setId(UUID.fromString(resultSet.getString("id")));
        document.setName(resultSet.getString("name"));
        document.setText(resultSet.getString("text"));
        document.setRegistrationNumber(resultSet.getString("registration_number"));
        document.setRegistrationDate(registrationDate);
        document.setAuthor(author);
        return document;
    }
}
