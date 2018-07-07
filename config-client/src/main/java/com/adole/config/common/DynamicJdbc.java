package com.adole.config.common;

import com.mysql.cj.jdbc.JdbcConnection;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

import javax.sql.DataSource;

public class DynamicJdbc {

    public JdbcProperties.Template getJdbcTemplate(DataSource dataSource, JdbcProperties properties){
//        return new JdbcConnection() {
//        Jdbc
        JdbcTemplateAutoConfiguration jdbcTemplateAutoConfiguration = new JdbcTemplateAutoConfiguration();
        return new JdbcProperties.Template();
    }
}
