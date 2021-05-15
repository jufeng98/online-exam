package org.javamaster.b2c.core.config;

import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.javamaster.b2c.core.consts.AppConsts;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author yudong
 * @date 2019/6/10
 */
@TestConfiguration
@Profile(PROFILE_UNIT_TEST)
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasAuthority(AppConsts.ROLE_ACTUATOR)
                .antMatchers("/public/**/*").permitAll()
                .antMatchers("/admin/users/createUsers").permitAll()
                .antMatchers("/core/files/uploadFile").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
    }

}

