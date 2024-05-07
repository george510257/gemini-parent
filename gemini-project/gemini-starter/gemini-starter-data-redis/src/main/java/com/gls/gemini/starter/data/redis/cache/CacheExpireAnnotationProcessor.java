package com.gls.gemini.starter.data.redis.cache;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存过期注解处理器
 */
@Getter
@Component
public class CacheExpireAnnotationProcessor implements BeanFactoryPostProcessor {
    /**
     * 缓存过期时间
     */
    private final Map<String, Duration> cacheExpires = new HashMap<>();

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
                    List<String> cacheNames = DefaultCacheResolver.getCacheNames(beanClass, method);
                    // 缓存过期时间
                    Duration duration = Duration.of(cacheExpire.timeToLive(), cacheExpire.timeUnit().toChronoUnit());
                    if (cacheNames != null) {
                        for (String cacheName : cacheNames) {
                            // 缓存过期时间
                            cacheExpires.put(cacheName, duration);
                        }
                    }
                }
            }, method -> method.isAnnotationPresent(CacheExpire.class));
        }
    }

}
