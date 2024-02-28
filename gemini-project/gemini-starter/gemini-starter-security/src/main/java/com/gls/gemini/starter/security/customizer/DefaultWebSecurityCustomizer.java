package com.gls.gemini.starter.security.customizer;

import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import jakarta.annotation.Resource;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.stereotype.Component;

/**
 * Web 安全定制
 */
@Component
public class DefaultWebSecurityCustomizer implements WebSecurityCustomizer {
    /**
     * 安全忽略属性
     */
    @Resource
    private SecurityIgnoreProperties securityIgnoreProperties;

    /**
     * 自定义 Web 安全
     *
     * @param web Web 安全配置器
     */
    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers(securityIgnoreProperties.getIgnoreUrls());
    }
}
