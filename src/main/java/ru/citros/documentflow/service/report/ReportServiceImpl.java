package ru.citros.documentflow.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.model.document.Document;
import ru.citros.documentflow.service.json.serialization.JsonSerializer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Предоставляет инструменты для отчета
 *
 * @param <T> тип объекта отчета
 * @author AIshmaev
 */
@Service
public class ReportServiceImpl<T extends Document> implements ReportService<T> {

    @Autowired
    private JsonSerializer<T> serializer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateReport(List<T> objects, String pathToDirectory) {
        Map<String, List<T>> documentsGroupedByAuthor = listToMap(objects, document -> document.getAuthor().getLastName());
        for (Map.Entry<String, List<T>> authorWithDocuments : documentsGroupedByAuthor.entrySet()) {
            String fullNameReport = pathToDirectory.concat(authorWithDocuments.getKey()).concat(".json");
            writeData(authorWithDocuments, new File(fullNameReport));
        }
    }

    /**
     * Записывает отчет в json формат
     *
     * @param data данные для записи
     * @param file куда записывать
     */
    private void writeData(Map.Entry<String, List<T>> data, File file) {
        serializer.serialize(file, data);
    }

    /**
     * Преобразует список в карту, метод в параметрах извлекает ключ из списка
     *
     * @param list      список объектов
     * @param extractor метд для извлечения ключа из списка
     * @return карта
     */
    private Map<String, List<T>> listToMap(List<T> list, Function<T, String> extractor) {
        return list.stream().collect(Collectors.groupingBy(extractor));
    }
}
