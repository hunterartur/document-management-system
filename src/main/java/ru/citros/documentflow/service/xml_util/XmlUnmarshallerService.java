package ru.citros.documentflow.service.xml_util;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.List;

/**
 * Предоставляет инструменты для анмаршалинга
 *
 * @param <T> тип
 * @author AIshmaev
 */
public interface XmlUnmarshallerService<T> {

    /**
     * Анмаршаллинг
     *
     * @param stream стрим
     * @return список объектов
     * @throws JAXBException
     */
    List<T> parse(InputStream stream);
}
