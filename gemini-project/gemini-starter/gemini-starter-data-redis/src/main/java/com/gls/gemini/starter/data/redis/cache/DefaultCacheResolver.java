package com.gls.gemini.starter.data.redis.cache;

import jakarta.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 默认缓存解析器
 */
@Component
public class DefaultCacheResolver extends AbstractCacheResolver {
    /**
     * 缓存过期注解处理器
     */
    @Resource
    private CacheExpireAnnotationProcessor cacheExpireAnnotationProcessor;

    public DefaultCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    /**
     * 获取缓存名称
     *
     * @param context 缓存操作上下文
     * @return 缓存名称
     */
    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        return cacheExpireAnnotationProcessor.getCacheNames(context.getTarget().getClass(), context.getMethod());
    }
}
