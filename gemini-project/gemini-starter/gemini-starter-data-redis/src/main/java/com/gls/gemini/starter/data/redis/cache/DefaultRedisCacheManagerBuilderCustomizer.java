package com.gls.gemini.starter.data.redis.cache;

import com.gls.gemini.starter.data.redis.constants.RedisProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

/**
 * 默认Redis缓存管理器构建器自定义器
 */
@Component
public class DefaultRedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {
    @Resource
    private RedisProperties redisProperties;
    @Resource
    private CacheExpireAnnotationProcessor cacheExpireAnnotationProcessor;
    @Resource
    private Jackson2JsonRedisSerializer<Object> jsonRedisSerializer;

    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        // 默认缓存配置
        RedisCacheConfiguration config = builder.cacheDefaults()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));
        builder.cacheDefaults(config);

        // 从配置文件中读取缓存配置
        for (RedisProperties.Cache cache : redisProperties.getCache()) {
            builder.withCacheConfiguration(cache.getCacheName(), config.entryTtl(cache.getTimeToLive()));
        }
        // 从注解中读取缓存配置
        for (Map.Entry<String, Duration> entry : cacheExpireAnnotationProcessor.getCacheExpires().entrySet()) {
            String cacheName = entry.getKey();
            Duration duration = entry.getValue();
            builder.withCacheConfiguration(cacheName, config.entryTtl(duration));
        }
    }
}
