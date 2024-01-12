package com.gls.gemini.starter.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Web配置
 */
@AutoConfiguration
public class WebConfig {

    /**
     * RestTemplate配置
     *
     * @return RestTemplate配置
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
