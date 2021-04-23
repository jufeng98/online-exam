package org.javamaster.b2c.core.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author yudong
 * @date 2021/3/24
 */
@TestConfiguration
@Profile(PROFILE_UNIT_TEST)
public class DatasourceTestConfig {

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


    @Bean("userDetailsService")
    public UserDetailsService detailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setEnableGroups(true);
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
