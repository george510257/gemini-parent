package com.gls.gemini.starter.security.config;

import cn.hutool.core.util.StrUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import com.gls.gemini.starter.security.support.WebSecurityHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * web安全配置
 */
@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebResourceServerConfig {
    /**
     * 安全工具
     *
     * @param securityIgnoreProperties 安全忽略属性
     * @param userDetailsService       用户详情服务
     * @return 安全工具
     */
    @Bean
    public WebSecurityHelper securityHelper(SecurityIgnoreProperties securityIgnoreProperties,
                                            UserDetailsService userDetailsService) {
        // 返回安全工具
        return new WebSecurityHelper(securityIgnoreProperties, userDetailsService);
    }

    /**
     * web安全定制器
     *
     * @param webSecurityHelper 安全工具
     * @return web安全定制器
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(WebSecurityHelper webSecurityHelper) {
        // 返回安全定制器
        return webSecurityHelper::webSecurityCustomizer;
    }

    /**
     * 默认安全过滤链
     *
     * @param http              HTTP安全
     * @param webSecurityHelper 安全工具
     * @return 安全过滤链
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, WebSecurityHelper webSecurityHelper) throws Exception {
        // 配置请求授权
        http.authorizeHttpRequests(webSecurityHelper::authorizeHttpRequestsCustomizer);
        // 关闭csrf
        http.csrf(webSecurityHelper::csrfCustomizer);
        // 配置资源服务器
        http.oauth2ResourceServer(webSecurityHelper::oauth2ResourceServerCustomizer);
        // 配置session管理
        http.sessionManagement(webSecurityHelper::sessionManagementCustomizer);
        // 配置异常处理
        http.exceptionHandling(webSecurityHelper::exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder(DiscoveryClient discoveryClient) {
        String jwkSetUri = discoveryClient.getServices().stream()
                .filter(serviceId -> serviceId.contains(CommonConstants.UAA_SERVICE_ID))
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                .map(serviceInstance -> StrUtil.format(SecurityConstants.URL_TEMPLATE, serviceInstance.getHost(), serviceInstance.getPort()))
                .findAny()
                .orElse("");
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        return new JwtAuthenticationConverter();
    }
}
