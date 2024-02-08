package com.gls.gemini.starter.data.redis;

import com.gls.gemini.starter.data.redis.constants.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * redis自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfig {
}
