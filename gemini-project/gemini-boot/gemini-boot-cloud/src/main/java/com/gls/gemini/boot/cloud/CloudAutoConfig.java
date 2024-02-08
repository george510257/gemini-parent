package com.gls.gemini.boot.cloud;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * cloud自动配置
 */
@Configuration
@ComponentScan
@EnableDiscoveryClient
public class CloudAutoConfig {
}
