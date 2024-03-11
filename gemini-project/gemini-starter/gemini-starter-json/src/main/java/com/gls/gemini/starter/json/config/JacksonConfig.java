package com.gls.gemini.starter.json.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gls.gemini.starter.json.support.CommonModule;
import com.gls.gemini.starter.json.support.DefaultDateFormat;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson 配置
 */
@Configuration
public class JacksonConfig {

    /**
     * Jackson 全局配置
     *
     * @return 自定义配置类
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 设置全局地区
            builder.locale(Locale.CHINA);
            // 设置全局时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 设置全局日期格式
            builder.dateFormat(new DefaultDateFormat());
            // 设置全局序列化模块 JavaTimeModule 用于序列化和反序列化 java8 时间类型
            builder.modules(new JavaTimeModule(), new CommonModule());
            // 反序列化时忽略未知属性
            builder.failOnUnknownProperties(false);
            // 序列化时忽略值为 null 的属性
            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        };
    }
}
