package com.gls.gemini.starter.data.redis.helper;

import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * Redisson 帮助类
 */
@Component
public class RedissonHelper {

    /**
     * Redisson 客户端
     */
    @Resource
    private RedissonClient redissonClient;

}
