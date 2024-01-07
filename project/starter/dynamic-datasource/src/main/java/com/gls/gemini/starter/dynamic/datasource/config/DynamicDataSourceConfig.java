package com.gls.gemini.starter.dynamic.datasource.config;

import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.gls.gemini.starter.dynamic.datasource.properties.DataSourceProviderProperties;
import com.gls.gemini.starter.dynamic.datasource.support.JdbcDataSourceProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;

/**
 * 动态数据源配置
 */
@AutoConfiguration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class DynamicDataSourceConfig {

    /**
     * 数据源提供者
     *
     * @param defaultDataSourceCreator     默认数据源创建器
     * @param dataSourceProperties         数据源配置
     * @param dataSourceProviderProperties 数据源提供者配置
     * @return 数据源提供者
     */
    @Bean
    public JdbcDataSourceProvider jdbcDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator,
                                                         DataSourceProperties dataSourceProperties,
                                                         DataSourceProviderProperties dataSourceProviderProperties) {
        return new JdbcDataSourceProvider(defaultDataSourceCreator, dataSourceProperties, dataSourceProviderProperties);
    }
}
