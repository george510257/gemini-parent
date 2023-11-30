package com.gls.gemini.starter.dynamic.datasource;

import com.gls.gemini.starter.dynamic.datasource.properties.DataSourceProviderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源自动配置
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({DataSourceProviderProperties.class})
public class DynamicDataSourceAutoConfig {
}
