package org.javamaster.b2c.core.config;

import org.javamaster.b2c.core.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private DataSource dataSource;

    private static final int SECONDS_OF_A_WEEK = 7 * 86400;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasAuthority("ROLE_ACTUATOR")
                .antMatchers("/core/**/*").authenticated()
                .antMatchers("/admin/**/*").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/core/login")
                .successHandler(loginHandler::onAuthenticationSuccess)
                .failureHandler(loginHandler::onAuthenticationFailure)
                .and()
                .rememberMe()
                .rememberMeCookieName("CORE_REMEMBER_ME")
                .rememberMeParameter("coreRememberMe")
                .key("coreKey")
                .tokenValiditySeconds(SECONDS_OF_A_WEEK)
                .and()
                .logout()
                .logoutUrl("/core/logout")
                .clearAuthentication(true)
                .logoutSuccessHandler(loginHandler::onLogoutSuccess)
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(loginHandler::onAuthenticationEntryPoint)
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .getUserDetailsService().setEnableGroups(true);
    }

    @Bean("userDetailsService")
    public UserDetailsService detailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

