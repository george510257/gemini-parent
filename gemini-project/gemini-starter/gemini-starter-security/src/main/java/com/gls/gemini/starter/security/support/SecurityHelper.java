package com.gls.gemini.starter.security.support;

import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

/**
 * 安全工具
 */
@RequiredArgsConstructor
public class SecurityHelper {

    private final SecurityIgnoreProperties securityIgnoreProperties;

    private final UserDetailsService userDetailsService;

    /**
     * 配置请求授权
     *
     * @param registry 请求授权注册器
     */
    public void authorizeHttpRequestsCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        // 授权请求 - 忽略
        registry.requestMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll();
        // 所有请求都需要认证
        registry.anyRequest().authenticated();
    }

    /**
     * 配置csrf
     *
     * @param configurer csrf配置器
     */
    public void csrfCustomizer(CsrfConfigurer<HttpSecurity> configurer) {
        // 禁用 CSRF
        configurer.disable();
    }

    /**
     * 配置资源服务器
     *
     * @param configurer 资源服务器配置器
     */
    public void oauth2ResourceServerCustomizer(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 配置资源服务器 JWT 验证
        configurer.jwt(this::jwtCustomizer);
    }

    /**
     * 配置资源服务器 JWT 验证
     *
     * @param configurer JWT 验证配置器
     */
    public void jwtCustomizer(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer configurer) {
        // 配置资源服务器 JWT 验证转换器
        configurer.jwtAuthenticationConverter(this::jwtAuthenticationConverter);
    }

    /**
     * JWT 认证转换器
     *
     * @param jwt JWT
     * @return 认证令牌
     */
    public AbstractAuthenticationToken jwtAuthenticationConverter(Jwt jwt) {
        // 获取用户名
        String username = jwt.getSubject();
        // 创建用户名密码认证令牌
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * 配置会话管理
     *
     * @param configurer 会话管理配置器
     */
    public void sessionManagementCustomizer(SessionManagementConfigurer<HttpSecurity> configurer) {
        // 会话创建策略 - 禁用
        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 配置异常处理
     *
     * @param configurer 异常处理配置器
     */
    public void exceptionHandlingCustomizer(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        // 配置异常处理 - Bearer Token 认证入口点
        configurer.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
        // 配置异常处理 - Bearer Token 访问拒绝处理器
        configurer.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
    }

    /**
     * web安全定制器
     *
     * @param webSecurity web安全
     */
    public void webSecurityCustomizer(WebSecurity webSecurity) {
        // 忽略请求
        webSecurity.ignoring().requestMatchers(securityIgnoreProperties.getIgnoreUrls());
    }
}
