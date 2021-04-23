package org.javamaster.b2c.core.config;

import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yudong
 * @date 2021/1/8
 */
@TestConfiguration
@PropertySource(value = "classpath:application-${env:dev}.properties")
@Profile(PROFILE_UNIT_TEST)
public class PropertyTestConfig {
}
