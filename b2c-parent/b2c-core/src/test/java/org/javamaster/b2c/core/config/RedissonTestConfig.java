package org.javamaster.b2c.core.config;

import lombok.SneakyThrows;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.net.URI;

/**
 * @author yudong
 * @date 2019/7/11
 */
@TestConfiguration
@Profile(PROFILE_UNIT_TEST)
public class RedissonTestConfig {

    @Value("${spring.test.redis.url}")
    private String url;
    @Value("${spring.test.redis.password}")
    private String password;

    @SneakyThrows
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        URI uri = new URI(url);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(uri.getHost(), uri.getPort());
        configuration.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public Config config() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(url)
                .setPassword(password);
        return config;
    }

    @Bean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }

}
