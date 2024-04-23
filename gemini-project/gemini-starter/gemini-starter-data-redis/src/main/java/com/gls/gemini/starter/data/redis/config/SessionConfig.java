package com.gls.gemini.starter.data.redis.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session配置
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = "gemini:session")
public class SessionConfig {
    /**
     * Redis序列化器
     */
    @Resource
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    /**
     * SpringSession默认Redis序列化器
     *
     * @return Redis序列化器
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(jackson2ObjectMapperBuilder.build(), Object.class);
    }
}
