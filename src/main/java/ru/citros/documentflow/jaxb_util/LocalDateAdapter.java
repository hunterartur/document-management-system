package ru.citros.documentflow.jaxb_util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Предоставляет инструмент для преобразования java.util.LocalDate в java.util.Date и обратно
 *
 * @author AIshmaev
 */
public class LocalDateAdapter extends XmlAdapter<Date, LocalDate> {

    /**
     * Преобразует java.util.Date в java.util.LocalDate
     *
     * @param date дата в формате java.util.Date
     * @return дату в формате java.util.LocalDate
     * @throws Exception
     */
    @Override
    public LocalDate unmarshal(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Преобразует java.util.LocalDate в java.util.Date
     *
     * @param localDate дата в формате java.util.LocalDate
     * @return дата в формате java.util.Date
     * @throws Exception
     */
    @Override
    public Date marshal(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
