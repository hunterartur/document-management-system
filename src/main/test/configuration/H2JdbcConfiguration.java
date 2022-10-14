package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.citros.documentflow.service.json.serialization.JsonSerializer;
import ru.citros.documentflow.service.json.serialization.JsonSerializerImpl;

/**
 * Конфигурация для тестов
 *
 * @author AIshmaev
 */
@Configuration
@Profile("test")
@PropertySource("classpath:application-integration-test.properties")
public class H2JdbcConfiguration {

    /**
     * Создает БД в ОЗУ
     *
     * @return БД в ОЗУ
     */
    @Bean
    public EmbeddedDatabase getTestEmbeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/H2.schema.sql")
                .addScript("classpath:sql/H2.data.sql")
                .build();
    }

    /**
     * Создает экземпляр JdbcTemplate с источником данных
     *
     * @return экземпляр JdbcTemplate
     */
    @Bean
    public JdbcTemplate getTestJdbcTemplate() {
        return new JdbcTemplate(getTestEmbeddedDatabase());
    }

    /**
     * Создает бин JsonSerializer
     *
     * @return эеземпляр JsonSerializer
     */
    @Bean
    public JsonSerializer<String> getTestJsonSerializer() {
        return new JsonSerializerImpl<>();
    }
}
