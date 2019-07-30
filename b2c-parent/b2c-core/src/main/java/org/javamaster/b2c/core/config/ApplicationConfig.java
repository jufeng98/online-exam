package org.javamaster.b2c.core.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yudong
 * @date 2019/7/11
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public Config config() {
        Config config = new Config();
        config.useSingleServer().setAddress(redisProperties.getUrl()).setPassword(redisProperties.getPassword());
        return config;
    }

    @Bean
    public RedissonClient redissonClient(Config config) {
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
