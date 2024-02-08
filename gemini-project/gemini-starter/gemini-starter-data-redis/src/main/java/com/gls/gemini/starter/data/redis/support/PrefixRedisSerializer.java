package com.gls.gemini.starter.data.redis.support;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 带前缀的 Redis 序列化器
 */
public class PrefixRedisSerializer extends StringRedisSerializer {

    /**
     * 前缀
     */
    private final String prefix;

    public PrefixRedisSerializer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String deserialize(byte[] bytes) {
        String value = super.deserialize(bytes);
        // 如果 value 为空，则返回 null
        if (value == null) {
            return null;
        }
        // 如果 value 包含前缀，则返回 value
        if (value.startsWith(this.prefix)) {
            return value;
        }
        // 返回前缀 + value
        return this.prefix + value;
    }

    @Override
    public byte[] serialize(String value) {
        // 如果 value 为空，则返回 null
        if (value == null) {
            return null;
        }
        // 如果 value 包含前缀，则返回 value
        if (value.startsWith(this.prefix)) {
            return super.serialize(value);
        }
        // 返回前缀 + value
        return super.serialize(this.prefix + value);
    }

}
