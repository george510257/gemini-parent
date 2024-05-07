package com.gls.gemini.starter.data.redis.helper;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        return values.stream().filter(tClass::isInstance).map(tClass::cast).collect(Collectors.toList());
    }

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取值
     *
     * @param key    键
     * @param tClass 类型
     * @param <T>    类型
     * @return 值
     */
    public <T> T getValue(String key, Class<T> tClass) {
        Object value = getValue(key);
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
     * 删除值
     *
     * @param key 键
     */
    public void deleteValue(String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 是否存在键
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 加锁
     *
     * @param key 键
     */
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        if (!lock.isLocked()) {
            lock.lock();
        }
    }

    /**
     * 解锁
     *
     * @param key 键
     */
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        if (lock.isLocked()) {
            lock.unlock();
        }
    }

    /**
     * 判断是否加锁
     *
     * @param key 键
     * @return 锁
     */
    public boolean isLocked(String key) {
        return redissonClient.getLock(key).isLocked();
    }

    /**
     * 计数器增加
     *
     * @param key   键
     * @param delta 增量
     */
    public Long increment(String key, long delta) {
        if (!hasKey(key)) {
            setValue(key, 0);
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 计数器加一
     *
     * @param key 键
     */
    public Long increment(String key) {
        return increment(key, 1);
    }

    /**
     * 计数器减少
     *
     * @param key          键
     * @param delta        减量
     * @param defaultValue 默认值
     */
    public Long decrement(String key, long delta, long defaultValue) {
        if (!hasKey(key)) {
            setValue(key, defaultValue);
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 计数器减少
     *
     * @param key   键
     * @param delta 减量
     */
    public Long decrement(String key, long delta) {
        return decrement(key, delta, 10000);
    }

    /**
     * 计数器减一
     *
     * @param key 键
     */
    public Long decrement(String key) {
        return decrement(key, 1);
    }
}
