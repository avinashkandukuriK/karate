package com.pharmacy.automation.config;

import com.pharmacy.automation.db.DbService;
import com.pharmacy.automation.db.DbTarget;
import com.pharmacy.automation.db.MultiDbRouter;
import java.util.EnumMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MultiDbProperties.class)
public class DatabaseConfig {

    private final MultiDbProperties properties;

    @Bean
    @Primary
    public DataSource mainDataSource() {
        return buildDataSource(properties.getMain());
    }

    @Bean
    public DataSource reportingDataSource() {
        return buildDataSource(properties.getReporting());
    }

    @Bean
    @Primary
    public NamedParameterJdbcTemplate mainJdbcTemplate(@Qualifier("mainDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate reportingJdbcTemplate(@Qualifier("reportingDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public MultiDbRouter multiDbRouter(@Qualifier("mainJdbcTemplate") NamedParameterJdbcTemplate main,
                                       @Qualifier("reportingJdbcTemplate") NamedParameterJdbcTemplate reporting) {
        Map<DbTarget, NamedParameterJdbcTemplate> map = new EnumMap<>(DbTarget.class);
        map.put(DbTarget.MAIN, main);
        map.put(DbTarget.REPORTING, reporting);
        return new MultiDbRouter(map);
    }

    @Bean
    public DbService dbService(MultiDbRouter router) {
        return new DbService(router);
    }

    @Bean
    public DataSourceInitializer mainInitializer(@Qualifier("mainDataSource") DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-main.sql"));
        populator.addScript(new ClassPathResource("data-main.sql"));
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    public DataSourceInitializer reportingInitializer(@Qualifier("reportingDataSource") DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-reporting.sql"));
        populator.addScript(new ClassPathResource("data-reporting.sql"));
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    private DataSource buildDataSource(MultiDbProperties.DataSourceSettings settings) {
        return DataSourceBuilder.create()
                .driverClassName(settings.getDriverClassName())
                .url(settings.getUrl())
                .username(settings.getUsername())
                .password(settings.getPassword())
                .build();
    }
}
