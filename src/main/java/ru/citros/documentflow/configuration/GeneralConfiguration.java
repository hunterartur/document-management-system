package ru.citros.documentflow.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

/**
 * Создает бины для работы приложения
 *
 * @author AIshmaev
 */
@Configuration
@Profile("dev")
public class GeneralConfiguration {

    @Autowired
    private DatabaseConfigurationProperties databaseConfigurationProperties;

    /**
     * Создает источник данных БД
     *
     * @return Источник данных БД
     */
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseConfigurationProperties.getDriverClassName());
        dataSource.setUrl(databaseConfigurationProperties.getUrl());
        dataSource.setUsername(databaseConfigurationProperties.getUsername());
        dataSource.setPassword(databaseConfigurationProperties.getPassword());
        return dataSource;
    }

    /**
     * Создает экземпляр JdbcTemplate с источником данных
     *
     * @return экземпляр JdbcTemplate
     */
    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    /**
     * Создает CorsFilter, который разрешает сервера, методы запросов, заголовки и тп к нашему приложению
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:63342");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
