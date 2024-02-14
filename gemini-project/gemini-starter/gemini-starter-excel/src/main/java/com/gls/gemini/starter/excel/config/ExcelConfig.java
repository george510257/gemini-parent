package com.gls.gemini.starter.excel.config;

import com.alibaba.excel.converters.longconverter.LongStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelConfig {

    /**
     * long类型转换为String类型
     *
     * @return LongStringConverter
     */
    @Bean
    public LongStringConverter longStringConverter() {
        return new LongStringConverter();
    }

}
