package com.gls.gemini.starter.data.redis.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存时间
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheExpire {
    /**
     * 失效时间 默认60秒
     *
     * @return 缓存时间
     */
    long timeToLive() default 60L;

    /**
     * 单位 默认秒
     *
     * @return 缓存单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
