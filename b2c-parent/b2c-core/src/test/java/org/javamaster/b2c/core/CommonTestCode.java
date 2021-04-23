package org.javamaster.b2c.core;

import org.javamaster.b2c.core.config.PropertyTestConfig;
import org.javamaster.b2c.core.initializer.SetEnvApplicationContextInitializer;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 公共测试基类
 *
 * @author yudong
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        PropertyTestConfig.class
}, initializers = SetEnvApplicationContextInitializer.class)
public class CommonTestCode {
    public static final String PROFILE_UNIT_TEST = "unit-test";
}
