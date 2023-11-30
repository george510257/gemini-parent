package com.gls.gemini.starter.core;

import com.gls.gemini.starter.core.properties.ExecutorThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 核心自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ExecutorThreadPoolProperties.class})
public class CoreAutoConfig {
}
