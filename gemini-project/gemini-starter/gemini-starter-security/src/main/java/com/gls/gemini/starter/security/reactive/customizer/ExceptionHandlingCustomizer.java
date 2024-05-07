package com.gls.gemini.starter.security.reactive.customizer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 异常处理定制器
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ExceptionHandlingCustomizer implements Customizer<ServerHttpSecurity.ExceptionHandlingSpec> {

    @Override
    public void customize(ServerHttpSecurity.ExceptionHandlingSpec spec) {
        // 配置认证入口点
        spec.authenticationEntryPoint(new BearerTokenServerAuthenticationEntryPoint());
        // 配置拒绝访问处理
        spec.accessDeniedHandler(new BearerTokenServerAccessDeniedHandler());
    }
}
