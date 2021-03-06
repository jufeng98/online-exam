package org.javamaster.b2c.core.config;

import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.filter.QrCodeLoginFilter;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

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
    @Autowired
    private QrCodeLoginFilter qrCodeLoginFilter;

    private static final int SECONDS_OF_A_WEEK = 7 * 86400;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasAuthority(AppConsts.ROLE_ACTUATOR)
                .antMatchers("/public/**/*").permitAll()
                .antMatchers("/admin/users/createUsers").permitAll()
                .antMatchers("/core/files/uploadFile").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/core/login")
                .successHandler(loginHandler::onAuthenticationSuccess)
                .failureHandler(loginHandler::onAuthenticationFailure)
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepositoryImpl(dataSource))
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
                .addFilterAfter(qrCodeLoginFilter, SecurityContextPersistenceFilter.class)
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
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setEnableGroups(true);
        return jdbcUserDetailsManager;
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

