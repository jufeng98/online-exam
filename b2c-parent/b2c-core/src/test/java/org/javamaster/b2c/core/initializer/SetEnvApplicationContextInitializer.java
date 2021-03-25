package org.javamaster.b2c.core.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author yudong
 * @date 2021/3/24
 */
public class SetEnvApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 修改这里实现使用不同的环境配置
        // dev,test
        System.setProperty("env", "dev");
    }
}
