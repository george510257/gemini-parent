package com.gls.gemini.sdk.core;

import com.gls.gemini.sdk.core.support.DefaultLoginTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SdkAutoConfig {

    /**
     * 登录用户模板
     *
     * @return 登录用户模板
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultLoginTemplate loginTemplate() {
        return new DefaultLoginTemplate();
    }
}
