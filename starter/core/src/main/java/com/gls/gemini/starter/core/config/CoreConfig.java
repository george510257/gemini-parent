package com.gls.gemini.starter.core.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 核心配置类
 */
@Configuration
public class CoreConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置缓存时间
        messageSource.setCacheSeconds(60);
        // 设置资源文件前缀
        messageSource.setBasenames("classpath:i18n/messages");
        return messageSource;
    }
}
