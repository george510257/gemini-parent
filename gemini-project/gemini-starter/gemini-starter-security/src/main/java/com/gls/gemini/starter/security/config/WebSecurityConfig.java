package com.gls.gemini.starter.security.config;

import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import com.gls.gemini.starter.security.support.SecurityHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

/**
 * web安全配置
 */
@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityConfig {
    /**
     * 安全工具
     *
     * @param securityIgnoreProperties 安全忽略属性
     * @param userDetailsService       用户详情服务
     * @return 安全工具
     */
    @Bean
    public SecurityHelper securityHelper(SecurityIgnoreProperties securityIgnoreProperties,
                                         UserDetailsService userDetailsService) {
        return new SecurityHelper(securityIgnoreProperties, userDetailsService);
    }

    /**
     * web安全定制器
     *
     * @param securityHelper 安全工具
     * @return web安全定制器
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(SecurityHelper securityHelper) {
        return securityHelper::webSecurityCustomizer;
    }

    /**
     * 默认安全过滤链
     *
     * @param http           HTTP安全
     * @param securityHelper 安全工具
     * @return 安全过滤链
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, SecurityHelper securityHelper) throws Exception {
        // 配置请求授权
        http.authorizeHttpRequests(securityHelper::authorizeHttpRequestsCustomizer);
        // 关闭csrf
        http.csrf(securityHelper::csrfCustomizer);
        // 配置资源服务器
        http.oauth2ResourceServer(securityHelper::oauth2ResourceServerCustomizer);
        // 配置session管理
        http.sessionManagement(securityHelper::sessionManagementCustomizer);
        // 配置异常处理
        http.exceptionHandling(securityHelper::exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

}
