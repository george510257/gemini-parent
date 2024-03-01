package com.gls.gemini.starter.security;

import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({SecurityIgnoreProperties.class})
public class SecurityAutoConfig {
}
