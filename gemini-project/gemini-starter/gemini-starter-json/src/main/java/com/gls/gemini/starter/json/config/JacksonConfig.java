package com.gls.gemini.starter.json.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.gemini.starter.json.support.CommonModule;
import com.gls.gemini.starter.json.support.DefaultDateFormat;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson 配置
 */
@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

    /**
     * Jackson 全局配置
     *
     * @return 自定义配置类
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 设置全局地区
            builder.locale(Locale.CHINA);
            // 设置全局时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 设置全局日期格式
            builder.dateFormat(new DefaultDateFormat());
            // 设置全局序列化模块
            builder.modules(new CommonModule());
            // 反序列化时忽略未知属性
            builder.failOnUnknownProperties(false);
            // 序列化时忽略值为 null 的属性
            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        };
    }
}
