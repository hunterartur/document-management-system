package ru.citros.documentflow.factory;

import org.springframework.beans.factory.annotation.Autowired;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.model.organizational_structure.Person;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.random.RandomService;

/**
 * Производитель документов
 *
 * @author AIshmaev
 */
public abstract class DocumentFactory {

    @Autowired
    protected RandomService randomService;

    @Autowired
    protected GeneralRepository<Person> personRepository;

    /**
     * Создает документ по типу
     *
     * @return документ
     */
    public abstract Document create();

    /**
     * Возвращает имя класса
     *
     * @return имя класса
     */
    public abstract String getName();

}
