package org.javamaster.b2c.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yudong
 * @date 2019/6/10
 */
@EnableRedisHttpSession
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "org.javamaster.b2c")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
