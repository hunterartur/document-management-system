package ru.citros.documentflow.jaxb_util;

import ru.citros.documentflow.model.organizational_structure.Staff;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Обертка над наследниками класса Staff
 *
 * @param <T> тип
 * @author AIshmaev
 */
@XmlRootElement(name = "wrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class StaffWrapper<T extends Staff> {

    /**
     * Коллекция определенного типа
     */
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return items;
    }
}
