package com.gls.gemini.starter.web.config;

import cn.hutool.core.date.DatePattern;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 增加GET请求参数中时间类型转换
     *
     * @param registry 注册器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DatePattern.NORM_TIME_FORMATTER);
        registrar.setDateFormatter(DatePattern.NORM_DATE_FORMATTER);
        registrar.setDateTimeFormatter(DatePattern.NORM_DATETIME_FORMATTER);
        registrar.registerFormatters(registry);
    }
}
