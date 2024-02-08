package com.gls.gemini.starter.data.redis.config;

import com.gls.gemini.starter.data.redis.constants.RedisProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.stream.Collectors;

@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * 自定义缓存管理器
     *
     * @param jsonRedisSerializer json序列化器
     * @return 自定义缓存管理器
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(Jackson2JsonRedisSerializer<Object> jsonRedisSerializer,
                                                           CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置json序列化器
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        // 设置缓存配置
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        // 设置缓存前缀
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        // 设置缓存空值
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        // 设置使用前缀
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    /**
     * 自定义缓存管理器构建器
     *
     * @param config          缓存配置
     * @param redisProperties redis配置
     * @return 自定义缓存管理器构建器
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(RedisCacheConfiguration config, RedisProperties redisProperties) {
        return builder -> builder.withInitialCacheConfigurations(redisProperties.getCache().stream().collect(
                Collectors.toMap(RedisProperties.Cache::getCacheName, cache -> config.entryTtl(cache.getTimeToLive()))));
    }

}
