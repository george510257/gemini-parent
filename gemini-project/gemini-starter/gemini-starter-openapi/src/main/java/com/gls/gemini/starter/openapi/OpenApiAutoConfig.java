package com.gls.gemini.starter.openapi;

import com.gls.gemini.starter.openapi.properties.OpenApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({OpenApiProperties.class})
public class OpenApiAutoConfig {
}
