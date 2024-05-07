package com.gls.gemini.starter.data.redis.config;

import com.gls.gemini.starter.data.redis.cache.DefaultCacheResolver;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 */
@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {
    @Resource
    private DefaultCacheResolver defaultCacheResolver;

    @Override
    public CacheResolver cacheResolver() {
        return defaultCacheResolver;
    }

}
