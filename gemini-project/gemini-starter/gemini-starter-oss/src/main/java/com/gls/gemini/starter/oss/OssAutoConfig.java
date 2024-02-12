package com.gls.gemini.starter.oss;

import com.gls.gemini.starter.oss.constants.OssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfig {
}