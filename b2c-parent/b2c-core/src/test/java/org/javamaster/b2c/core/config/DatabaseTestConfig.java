package org.javamaster.b2c.core.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author yudong
 * @date 2021/3/24
 */
@TestConfiguration
public class DatabaseTestConfig {

    @Value("${spring.test.datasource.url}")
    private String url;
    @Value("${spring.test.datasource.username}")
    private String username;
    @Value("${spring.test.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
