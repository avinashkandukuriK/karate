package com.pharmacy.automation.db;

import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MultiDbRouter {

    private final Map<DbTarget, NamedParameterJdbcTemplate> templates;

    public MultiDbRouter(Map<DbTarget, NamedParameterJdbcTemplate> templates) {
        this.templates = templates;
    }

    public NamedParameterJdbcTemplate route(DbTarget target) {
        NamedParameterJdbcTemplate template = templates.get(target);
        if (template == null) {
            throw new IllegalArgumentException("No JDBC template configured for target: " + target);
        }
        return template;
    }
}
