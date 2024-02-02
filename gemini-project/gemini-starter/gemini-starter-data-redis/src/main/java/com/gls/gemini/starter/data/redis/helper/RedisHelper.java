package com.gls.gemini.starter.data.redis.helper;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis 帮助类
 */
@Component
public class RedisHelper {

    @Value("${spring.redis.prefix:redis:}")
    private String prefix = "redis:";

    /**
     * RedisTemplate
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取带前缀的 key
     *
     * @param key key
     * @return 带前缀的 key
     */
    public String getPrefixKey(String key) {
        return prefix + key;
    }

    /**
     * 删除 key
     *
     * @param key key
     */
    public void delete(String key) {
        redisTemplate.delete(getPrefixKey(key));
    }

    /**
     * 判断 key 是否存在
     *
     * @param key key
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getPrefixKey(key)));
    }

    /**
     * 设置 key-value
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时时间
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(getPrefixKey(key), value, timeout);
    }

    /**
     * 获取 key-value
     *
     * @param key key
     * @return value
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(getPrefixKey(key));
    }

    /**
     * 获取 key-value
     *
     * @param key key
     * @param <T> value 类型
     * @return value
     */
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(getPrefixKey(key));
    }

    /**
     * 设置 key-value
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(getPrefixKey(key), value);
    }

    /**
     * 获取递增值
     *
     * @param key key
     * @return 递增后的值
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(getPrefixKey(key));
    }

    /**
     * 获取递增值
     *
     * @param key   key
     * @param delta 递增值
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(getPrefixKey(key), delta);
    }

    /**
     * 获取列表
     *
     * @param key key
     * @return 列表
     */
    public List<Object> getList(String key) {
        Long size = redisTemplate.opsForList().size(getPrefixKey(key));
        if (size == null || size == 0) {
            return null;
        }
        return redisTemplate.opsForList().range(getPrefixKey(key), 0, size);
    }

}
