package com.gls.gemini.starter.security.config;

import com.gls.gemini.starter.security.customizer.*;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * web安全配置
 */
@AutoConfiguration
@EnableWebSecurity
public class SecurityWebConfig {
    /**
     * 授权请求定制
     */
    @Resource
    private AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer;
    /**
     * csrf定制
     */
    @Resource
    private CsrfCustomizer csrfCustomizer;
    /**
     * 资源服务器定制
     */
    @Resource
    private OAuth2ResourceServerCustomizer oauth2ResourceServerCustomizer;
    /**
     * 会话管理定制
     */
    @Resource
    private SessionManagementCustomizer sessionManagementCustomizer;
    /**
     * 异常处理定制
     */
    @Resource
    private ExceptionHandlingCustomizer exceptionHandlingCustomizer;

    /**
     * 默认安全过滤链
     *
     * @param http HTTP安全
     * @return 安全过滤链
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置请求授权
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // 关闭csrf
        http.csrf(csrfCustomizer);
        // 配置资源服务器
        http.oauth2ResourceServer(oauth2ResourceServerCustomizer);
        // 配置session管理
        http.sessionManagement(sessionManagementCustomizer);
        // 配置异常处理
        http.exceptionHandling(exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

}
