package com.gls.gemini.starter.data.redis.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 默认缓存解析器
 */
@Component
public class DefaultCacheResolver extends AbstractCacheResolver {

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
        // 获取缓存名称
        List<String> cacheNames = new ArrayList<>();
        // 获取类名
        String className = context.getTarget().getClass().getSimpleName();
        // 将类名转换为下划线格式
        cacheNames.add(StrUtil.toUnderlineCase(className));
        // 添加缓存名称
        cacheNames.addAll(context.getOperation().getCacheNames());
        return cacheNames;
    }
}
