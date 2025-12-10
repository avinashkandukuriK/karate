package com.pharmacy.automation.db;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DbService {

    private final MultiDbRouter router;

    public DbService(MultiDbRouter router) {
        this.router = router;
    }

    public List<Map<String, Object>> query(DbTarget target, String sql, Map<String, Object> params) {
        return router.route(target).queryForList(sql, new MapSqlParameterSource(params));
    }

    public <T> Optional<T> queryForObject(DbTarget target, String sql, Map<String, Object> params, Class<T> type) {
        List<T> result = router.route(target).query(sql, new MapSqlParameterSource(params), BeanPropertyRowMapper.newInstance(type));
        return result.stream().findFirst();
    }

    public int update(DbTarget target, String sql, Map<String, Object> params) {
        return router.route(target).update(sql, new MapSqlParameterSource(params));
    }
}
