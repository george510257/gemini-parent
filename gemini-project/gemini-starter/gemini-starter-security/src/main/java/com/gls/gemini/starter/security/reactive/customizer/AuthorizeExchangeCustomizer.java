package com.gls.gemini.starter.security.reactive.customizer;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 授权交换定制器
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class AuthorizeExchangeCustomizer implements Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(ServerHttpSecurity.AuthorizeExchangeSpec spec) {
        // 忽略配置的url
        if (securityProperties.getIgnoreUrls() != null) {
            spec.pathMatchers(securityProperties.getIgnoreUrls()).permitAll();
        }
        // 其他请求需要认证
        spec.anyExchange().authenticated();
    }
}
