package ru.citros.documentflow.model.organizational_structure;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * Сущность которая хранит элемент организационной структуры (Staff)
 *
 * @author AIshmaev
 */
@XmlTransient
@XmlSeeAlso({Person.class, Organization.class, Department.class})
public abstract class Staff {

    /**
     * Идентификатор
     */
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Staff: id={0}", id);
    }
}
