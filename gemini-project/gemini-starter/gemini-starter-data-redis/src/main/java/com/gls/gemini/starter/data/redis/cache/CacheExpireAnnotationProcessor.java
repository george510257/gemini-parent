package com.gls.gemini.starter.data.redis.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

/**
 * 缓存过期注解处理器
 */
@Slf4j
@Component
public class CacheExpireAnnotationProcessor implements BeanFactoryPostProcessor {
    /**
     * 缓存过期时间
     */
    @Getter
    private final Map<String, Duration> cacheExpireMap = new HashMap<>();
    /**
     * 缓存操作源
     */
    private final AnnotationCacheOperationSource cacheOperationSource = new AnnotationCacheOperationSource(false);

    /**
     * 处理缓存过期注解
     *
     * @param beanFactory bean工厂.
     * @throws BeansException bean异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取所有bean名称
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            // 获取bean的类型
            Class<?> beanClass = beanFactory.getType(beanName);
            // 如果bean类型为空则跳过
            if (beanClass == null) {
                continue;
            }
            // 处理bean的方法
            ReflectionUtils.doWithMethods(beanClass, method -> {
                // 如果方法上有CacheExpire注解
                CacheExpire cacheExpire = method.getAnnotation(CacheExpire.class);
                if (cacheExpire != null) {
                    // 获取缓存名称
                    List<String> cacheNames = getCacheNames(beanClass, method);
                    // 缓存过期时间
                    Duration duration = Duration.of(cacheExpire.timeToLive(), cacheExpire.timeUnit().toChronoUnit());
                    if (cacheNames != null) {
                        for (String cacheName : cacheNames) {
                            // 缓存过期时间
                            cacheExpireMap.put(cacheName, duration);
                        }
                    }
                }
            }, method -> method.isAnnotationPresent(CacheExpire.class));
        }
    }

    /**
     * 获取缓存名称
     *
     * @param beanClass bean类型
     * @param method    方法
     * @return 缓存名称
     */
    public List<String> getCacheNames(Class<?> beanClass, Method method) {
        // 获取缓存操作
        List<String> cacheNames = new ArrayList<>();
        Collection<CacheOperation> cacheOperations = cacheOperationSource.getCacheOperations(method, beanClass);
        if (CollUtil.isNotEmpty(cacheOperations)) {
            // 获取缓存名称
            cacheNames = cacheOperations.stream().map(CacheOperation::getCacheNames)
                    .flatMap(Collection::stream).distinct().toList();
            log.info("cacheNames: {}", cacheNames);
        }
        if (CollUtil.isEmpty(cacheNames)) {
            // 获取类名
            String className = beanClass.getSimpleName();
            // 将类名转换为下划线格式
            String upperCase = StrUtil.toSymbolCase(className, ':');
            cacheNames = CollUtil.newArrayList(upperCase + ":default");
        }
        return cacheNames;
    }
}
