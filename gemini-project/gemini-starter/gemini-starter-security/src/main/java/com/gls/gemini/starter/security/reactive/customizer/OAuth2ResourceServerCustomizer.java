package com.gls.gemini.starter.security.reactive.customizer;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

/**
 * OAuth2资源服务器定制器
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class OAuth2ResourceServerCustomizer implements Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec> {
    /**
     * jwt认证转换器提供者
     */
    @Resource
    private ObjectProvider<JwtAuthenticationConverter> jwtAuthenticationConverterObjectProvider;

    @Override
    public void customize(ServerHttpSecurity.OAuth2ResourceServerSpec spec) {
        // 配置jwt
        spec.jwt(this::jwtCustomizer);
    }

    private void jwtCustomizer(ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec spec) {
        // 配置jwt认证转换器
        jwtAuthenticationConverterObjectProvider.ifAvailable(spec::jwtAuthenticationConverter);
    }

}
