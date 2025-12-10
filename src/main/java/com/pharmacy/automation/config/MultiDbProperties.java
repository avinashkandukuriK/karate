package com.pharmacy.automation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "multidb")
public class MultiDbProperties {

    private DataSourceSettings main = new DataSourceSettings();
    private DataSourceSettings reporting = new DataSourceSettings();

    @Data
    public static class DataSourceSettings {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
}
