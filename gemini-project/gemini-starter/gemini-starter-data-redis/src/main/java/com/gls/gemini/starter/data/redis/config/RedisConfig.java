package com.gls.gemini.starter.data.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Redis 配置类
 */

@Configuration
public class RedisConfig {

    /**
     * json序列化器
     *
     * @param jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder 实例
     * @return json序列化器
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用默认类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * RedisTemplate 配置
     *
     * @param redisConnectionFactory redis连接工厂
     * @param jsonRedisSerializer    json序列化器
     * @return RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
