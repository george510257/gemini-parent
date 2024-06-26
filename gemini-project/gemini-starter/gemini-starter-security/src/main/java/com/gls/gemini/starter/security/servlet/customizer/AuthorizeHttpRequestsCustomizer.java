package com.gls.gemini.starter.security.servlet.customizer;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AuthorizeHttpRequestsCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        // 配置请求授权 - 忽略请求
        if (securityProperties.getIgnoreUrls() != null) {
            registry.requestMatchers(securityProperties.getIgnoreUrls()).permitAll();
        }
        // 配置请求授权 - 其他请求
        registry.anyRequest().permitAll();
    }
}
