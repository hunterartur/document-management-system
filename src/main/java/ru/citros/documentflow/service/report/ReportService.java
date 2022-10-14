package ru.citros.documentflow.service.report;

import java.util.List;

/**
 * Предоставляет инструменты для отчета
 *
 * @param <T> тип объекта отчета
 * @author AIshmaev
 */
public interface ReportService<T> {

    /**
     * Генерирует отчет
     *
     * @param objects         список документов
     * @param pathToDirectory путь к каталогу отчетов
     * @return отчет в текстовом формате
     */
    void generateReport(List<T> objects, String pathToDirectory);
}
