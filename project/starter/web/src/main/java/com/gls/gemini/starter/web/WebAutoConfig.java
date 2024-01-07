package com.gls.gemini.starter.web;

import com.gls.gemini.starter.web.result.ResultIgnoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web自动配置
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ResultIgnoreProperties.class})
public class WebAutoConfig {
}
