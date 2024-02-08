package com.gls.gemini.starter.data.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.gls.gemini.starter.data.redis.constants.RedisProperties;
import com.gls.gemini.starter.data.redis.helper.RedisHelper;
import com.gls.gemini.starter.data.redis.helper.RedissonHelper;
import com.gls.gemini.starter.data.redis.support.PrefixRedisSerializer;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Redis 配置类
 */

@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    /**
     * 带前缀的 Redis 序列化器
     *
     * @return
     */
    @Bean
    public PrefixRedisSerializer prefixRedisSerializer() {
        return new PrefixRedisSerializer(redisProperties.getPrefix());
    }

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
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * RedisTemplate 配置
     *
     * @param redisConnectionFactory redis连接工厂
     * @param prefixRedisSerializer  带前缀的 Redis 序列化器
     * @param jsonRedisSerializer    json序列化器
     * @return RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       PrefixRedisSerializer prefixRedisSerializer,
                                                       Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(prefixRedisSerializer);
        redisTemplate.setHashKeySerializer(prefixRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * StringRedisTemplate 配置
     *
     * @param redisConnectionFactory redis连接工厂
     * @return StringRedisTemplate 实例
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    /**
     * Redis 帮助类
     *
     * @param redisTemplate redisTemplate 实例
     * @return RedisHelper 实例
     */
    @Bean
    public RedisHelper redisHelper(RedisTemplate<String, Object> redisTemplate) {
        return new RedisHelper(redisProperties.getPrefix(), redisTemplate);
    }

    /**
     * Redisson 帮助类
     *
     * @param redissonClient redissonClient 实例
     * @return RedissonHelper 实例
     */
    @Bean
    public RedissonHelper redissonHelper(RedissonClient redissonClient) {
        return new RedissonHelper(redisProperties.getLockPrefix(), redissonClient);
    }
}
