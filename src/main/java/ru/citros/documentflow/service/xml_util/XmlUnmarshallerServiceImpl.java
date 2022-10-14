package ru.citros.documentflow.service.xml_util;

import org.springframework.stereotype.Service;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.jaxb_util.StaffWrapper;
import ru.citros.documentflow.model.organizational_structure.Staff;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * Предоставляет инструменты для анмаршалинга
 *
 * @param <T> тип должен быть дочерним Staff
 * @author AIshmaev
 */
@Service
public class XmlUnmarshallerServiceImpl<T extends Staff> implements XmlUnmarshallerService<T> {

    /**
     * Анмаршаллер
     */
    private Unmarshaller unmarshaller;

    public XmlUnmarshallerServiceImpl() {
        try {
            unmarshaller = JAXBContext.newInstance(StaffWrapper.class).createUnmarshaller();
        } catch (JAXBException e) {
            throw new DocumentFlowException(MessageFormat.format("Ошибка при создании экзепляра класса: {0}!",
                    JAXBContext.class.getName()), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> parse(InputStream stream) {
        try {
            return ((StaffWrapper<T>) unmarshaller.unmarshal(stream)).getItems();
        } catch (JAXBException e) {
            throw new DocumentFlowException("Ошибка при анмаршаллинге потока!", e);
        }
    }
}
