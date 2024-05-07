package com.gls.gemini.starter.security.servlet;

import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.servlet.customizer.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * web安全配置
 */
@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ResourceServerConfig {

    /**
     * 默认安全过滤链
     *
     * @param http HTTP安全
     * @return 安全过滤链
     * @throws Exception 异常
     */
    @Bean
    @Order
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http,
                                                      AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                      CsrfCustomizer csrfCustomizer,
                                                      OAuth2ResourceServerCustomizer oauth2ResourceServerCustomizer,
                                                      SessionManagementCustomizer sessionManagementCustomizer,
                                                      ExceptionHandlingCustomizer exceptionHandlingCustomizer) throws Exception {
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

    /**
     * jwt 解码器
     *
     * @param discoveryClient 服务发现客户端
     * @return jwt 解码器
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder(DiscoveryClient discoveryClient) {
        String jwkSetUri = discoveryClient.getServices().stream()
                // 过滤服务ID
                .filter(serviceId -> serviceId.contains(CommonConstants.UAA_SERVICE_ID))
                // 获取服务实例
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                // 获取服务实例地址
                .map(serviceInstance -> serviceInstance.getUri().toString() + SecurityConstants.JWK_SET_ENDPOINT)
                // 获取任意一个
                .findAny()
                // 获取不到则返回空字符串
                .orElse("");
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

}
