package org.javamaster.b2c.core.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yudong
 * @date 2021/1/8
 */
@TestConfiguration
@PropertySource(value = "classpath:application-${env:dev}.properties")
public class PropertyTestConfig {
}
