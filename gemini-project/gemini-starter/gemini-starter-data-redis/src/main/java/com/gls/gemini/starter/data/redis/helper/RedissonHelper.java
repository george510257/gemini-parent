package com.gls.gemini.starter.data.redis.helper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 帮助类
 */
@RequiredArgsConstructor
public class RedissonHelper {

    private final String prefix;

    /**
     * Redisson 客户端
     */
    @Delegate
    private final RedissonClient redissonClient;


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
     * 尝试获取锁
     *
     * @param key 锁的 key
     * @return 是否获取成功
     */
    public boolean tryLock(String key) {
        String lockKey = getPrefixKey(key);
        return redissonClient.getLock(lockKey).tryLock();
    }

    /**
     * 尝试获取锁
     *
     * @param key       锁的 key
     * @param waitTime  等待时间
     * @param leaseTime 锁的租约时间
     * @param unit      时间单位
     * @return 是否获取成功
     */
    @SneakyThrows
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        String lockKey = getPrefixKey(key);
        return redissonClient.getLock(lockKey).tryLock(waitTime, leaseTime, unit);
    }

    /**
     * 释放锁
     *
     * @param key 锁的 key
     */
    public boolean unlock(String key) {
        String lockKey = getPrefixKey(key);
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
            return true;
        }
        return false;
    }

    /**
     * 判断是否有锁
     */
    public boolean isLocked(String key) {
        String lockKey = getPrefixKey(key);
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }

}
