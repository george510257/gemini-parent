package com.gls.gemini.starter.excel;

import com.gls.gemini.starter.excel.constants.ExcelProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Excel自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ExcelProperties.class})
public class ExcelAutoConfig {
}