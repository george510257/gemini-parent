package com.gls.gemini.starter.core.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.starter.core.def.DefaultLoginTemplate;
import com.gls.gemini.starter.core.properties.SnowflakeProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 核心配置类
 */
@Configuration
public class CoreConfig {

    /**
     * 国际化配置
     *
     * @return MessageSource 国际化配置
     */
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

    /**
     * 雪花算法
     *
     * @param snowflakeProperties 雪花算法配置
     * @return Snowflake 雪花算法
     */
    @Bean
    public Snowflake snowflake(SnowflakeProperties snowflakeProperties) {
        if (snowflakeProperties.isEnabled()) {
            long dataCenterId = IdUtil.getDataCenterId(snowflakeProperties.getMaxDataCenterId());
            long workerId = IdUtil.getWorkerId(dataCenterId, snowflakeProperties.getMaxWorkerId());
            return IdUtil.getSnowflake(workerId, dataCenterId);
        }
        return IdUtil.getSnowflake();
    }

    @Bean
    public LoginTemplate<?, ?, ?, ?> loginTemplate() {
        return new DefaultLoginTemplate();
    }
}
