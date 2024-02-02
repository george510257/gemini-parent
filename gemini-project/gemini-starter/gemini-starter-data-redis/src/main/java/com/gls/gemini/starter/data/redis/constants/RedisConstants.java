package com.gls.gemini.starter.data.redis.constants;

/**
 * Redis 常量
 */
public interface RedisConstants {

    /**
     * 缓存前缀
     */
    String CACHE_PREFIX = "gemini:cache:";

    /**
     * 缓存分隔符
     */
    String CACHE_SEPARATOR = ":";

    /**
     * redis 锁前缀
     */
    String LOCK_PREFIX = "gemini:lock:";
}
