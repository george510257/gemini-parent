package com.gls.gemini.starter.data.redis.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis 配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".redis")
public class RedisProperties extends BaseProperties {

    /**
     * 缓存配置
     */
    private List<Cache> cache = new ArrayList<>();

    @Data
    public static class Cache implements Serializable {

        /**
         * 缓存名称
         */
        private String cacheName;

        /**
         * 缓存时间 30分钟
         */
        private Duration timeToLive = Duration.ofMinutes(30);
    }
}
