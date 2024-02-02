package com.gls.gemini.starter.data.redis.config;

import com.gls.gemini.starter.data.redis.support.DefaultCacheResolver;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * 缓存解析器
     *
     * @param cacheManager 缓存管理器
     * @return 缓存解析器
     */
    @Bean
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new DefaultCacheResolver(cacheManager);
    }

    /**
     * 缓存配置
     *
     * @param jsonRedisSerializer json序列化器
     * @return 缓存配置
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 默认缓存时间 1小时
                .entryTtl(Duration.ofHours(1L))
                // 序列化器 key
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                // 序列化器 value
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));
    }

}
