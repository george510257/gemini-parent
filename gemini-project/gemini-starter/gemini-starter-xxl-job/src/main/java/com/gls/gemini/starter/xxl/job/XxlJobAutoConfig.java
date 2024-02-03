package com.gls.gemini.starter.xxl.job;

import com.gls.gemini.starter.xxl.job.constants.XxlJobProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({XxlJobProperties.class})
public class XxlJobAutoConfig {
}
