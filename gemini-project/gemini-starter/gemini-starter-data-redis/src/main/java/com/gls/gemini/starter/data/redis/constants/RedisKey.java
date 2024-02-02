package com.gls.gemini.starter.data.redis.constants;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RedisKey implements Serializable {
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 名称
     */
    private String name;

    @Override
    public String toString() {
        return prefix + ":" + name;
    }
}
