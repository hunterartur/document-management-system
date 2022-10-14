package ru.citros.documentflow.service.random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.configuration.CommonConfigurationProperties;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Предоставляет инструменты для работы с документом и списком документов.
 *
 * @author AIshmaev
 */
@Service
public class RandomServiceImpl implements RandomService {

    @Autowired
    private CommonConfigurationProperties properties;

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateNumericLetterRandomString() {
        return RandomStringUtils.randomAlphanumeric(properties.getLengthGeneratedString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateLetterRandomString() {
        return RandomStringUtils.randomAlphabetic(properties.getLengthGeneratedString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateNumericRandomString() {
        return RandomStringUtils.randomNumeric(properties.getLengthGeneratedString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean generateBooleanRandomValue() {
        return Math.random() < 0.5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate generateRandomDate() {
        return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(properties.getDateInterval(), 2 * properties.getDateInterval()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int generateRandomNumber() {
        return Integer.parseInt(RandomStringUtils.randomNumeric(1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T selectRandomElementFromCollection(List<? extends T> collection) {
        return collection.get((int) (0 + Math.random() * collection.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Enum<T>> T generateRandomElementFromArrayOfEnum(T[] values) {
        return values[(int) (0 + Math.random() * values.length)];
    }
}
