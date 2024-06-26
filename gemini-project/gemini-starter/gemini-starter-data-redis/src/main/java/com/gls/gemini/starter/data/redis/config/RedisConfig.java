package com.gls.gemini.starter.data.redis.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置类
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private Jackson2JsonRedisSerializer<Object> jsonRedisSerializer;

    /**
     * RedisTemplate 配置
     */
    @PostConstruct
    public void init() {
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
    }

}
