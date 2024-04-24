package com.gls.gemini.starter.data.redis.helper;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis 帮助类
 */
@Component
public class RedisHelper {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 设置值
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void putHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 删除值
     *
     * @param key   键
     * @param field 字段
     */
    public void deleteHash(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 获取值
     *
     * @param key    键
     * @param field  字段
     * @param tClass 类型
     * @param <T>    类型
     * @return 值
     */
    public <T> T getHash(String key, String field, Class<T> tClass) {
        Object value = redisTemplate.opsForHash().get(key, field);
        if (value == null) {
            return null;
        }
        try {
            return tClass.cast(value);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * 获取所有值
     *
     * @param key    键
     * @param tClass 类型
     * @param <T>    类型
     * @return 值
     */
    public <T> List<T> valuesHash(String key, Class<T> tClass) {
        List<Object> values = redisTemplate.opsForHash().values(key);
        if (CollUtil.isEmpty(values)) {
            return null;
        }
        return values.stream().filter(tClass::isInstance).map(tClass::cast).toList();
    }
}
