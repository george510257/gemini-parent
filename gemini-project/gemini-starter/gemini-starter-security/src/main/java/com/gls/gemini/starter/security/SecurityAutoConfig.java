package com.gls.gemini.starter.security;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityAutoConfig {
}
