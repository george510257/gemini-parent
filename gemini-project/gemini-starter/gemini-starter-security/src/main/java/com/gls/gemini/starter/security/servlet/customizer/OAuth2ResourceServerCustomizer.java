package com.gls.gemini.starter.security.servlet.customizer;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class OAuth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {
    @Resource
    private ObjectProvider<JwtAuthenticationConverter> jwtAuthenticationConverterProvider;

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 配置jwt
        configurer.jwt(this::jwtCustomizer);
    }

    private void jwtCustomizer(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer configurer) {
        jwtAuthenticationConverterProvider.ifAvailable(configurer::jwtAuthenticationConverter);
    }
}
