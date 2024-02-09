package com.gls.gemini.starter.aliyun.oss;

import com.gls.gemini.starter.aliyun.oss.constants.OssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * oss自动配置
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfig {
}