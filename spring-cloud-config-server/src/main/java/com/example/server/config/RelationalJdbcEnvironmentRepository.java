package com.example.server.config;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 基于jdbc加载当前配置的关联配置
 *
 * @see org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository
 */
@Profile("jdbc")
@Configuration
public class RelationalJdbcEnvironmentRepository implements EnvironmentRepository, Ordered {

    private final JdbcTemplate jdbc;
    private final MapResultSetExtractor extractorToMap = new MapResultSetExtractor();
    private final PropertiesResultSetExtractor extractor = new PropertiesResultSetExtractor();
    @Setter
    @Getter
    private int order;
    @Setter
    @Getter
    private String sql;

    private static final String FIELD_APPLICATION = "application_";
    private static final String FIELD_PROFILE = "profile_";
    private static final String FIELD_LABEL = "label_";
    private static final String QUERY_REL_CONFIG_SQL =
            "SELECT id_, application_, profile_, label_, type_, value_ " +
                    "FROM properties_desc " +
                    "WHERE id_ = (SELECT rel_ FROM properties_desc WHERE application_ = ? AND profile_ = ? AND label_ = ?)";

    public RelationalJdbcEnvironmentRepository(JdbcTemplate jdbc,
                                               JdbcEnvironmentProperties properties) {
        this.jdbc = jdbc;
        this.order = properties.getOrder() + 1;
        this.sql = properties.getSql();
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        if (StringUtils.isEmpty(application)) {
            throw new IllegalArgumentException("The application parameter cannot be null");
        }
        if (StringUtils.isEmpty(label)) {
            label = "master";
        }
        if (StringUtils.isEmpty(profile)) {
            profile = "default";
        }
        String[] profiles = StringUtils.commaDelimitedListToStringArray(profile);
        Environment environment = new Environment(application, profiles, label, null, null);
        while (true) {
            Map<String, String> map = jdbc.query(QUERY_REL_CONFIG_SQL, new Object[]{application, profile, label}, this.extractorToMap);
            if (Objects.isNull(map) || map.isEmpty()) {
                //不存在关联配置了
                break;
            }
            application = Objects.toString(map.get(FIELD_APPLICATION));
            profile = Objects.toString(map.get(FIELD_PROFILE));
            label = Objects.toString(map.get(FIELD_LABEL));
            Map<String, String> config = jdbc.query(sql, new Object[]{application, profile, label}, this.extractor);
            if (!Objects.isNull(config) && !config.isEmpty()) {
                //order 的加载顺序和 addFirst方法共同保证了 当前配置的优先级高于依赖配置优先级
                //客户端获取配置时，会去算name的hash值，所以添加了label标识为了区分多个label
                environment.addFirst(new PropertySource(application + "-" + profile + "-" + label, config));
            }
        }
        return environment;
    }
}

class MapResultSetExtractor implements ResultSetExtractor<Map<String, String>> {

    @Override
    public Map<String, String> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<String, String> map = new LinkedHashMap<>();
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columns; ++i) {
                map.put(md.getColumnName(i), rs.getString(i));
            }
        }
        return map;
    }

}

class PropertiesResultSetExtractor implements ResultSetExtractor<Map<String, String>> {

    @Override
    public Map<String, String> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<String, String> map = new LinkedHashMap<>();
        while (rs.next()) {
            map.put(rs.getString(1), rs.getString(2));
        }
        return map;
    }

}