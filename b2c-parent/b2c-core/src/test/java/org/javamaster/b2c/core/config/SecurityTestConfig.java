package org.javamaster.b2c.core.config;

import org.javamaster.b2c.core.consts.AppConsts;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yudong
 * @date 2019/6/10
 */
@Order(99)
@TestConfiguration
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

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

