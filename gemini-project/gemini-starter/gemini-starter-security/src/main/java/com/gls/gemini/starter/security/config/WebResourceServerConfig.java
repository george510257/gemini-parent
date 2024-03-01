package com.gls.gemini.starter.security.config;

import cn.hutool.core.util.StrUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import com.gls.gemini.starter.security.converter.WebJwtAuthenticationConverter;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

/**
 * web安全配置
 */
@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebResourceServerConfig {
    /**
     * 安全忽略属性
     */
    @Resource
    private SecurityIgnoreProperties securityIgnoreProperties;
    /**
     * 用户详情服务
     */
    @Resource
    private Optional<UserDetailsService> userDetailsService;

    /**
     * web安全定制器
     *
     * @return web安全定制器
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 返回安全定制器
        return webSecurity -> webSecurity.ignoring().requestMatchers(securityIgnoreProperties.getIgnoreUrls());
    }

    /**
     * 默认安全过滤链
     *
     * @param http HTTP安全
     * @return 安全过滤链
     * @throws Exception 异常
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置请求授权
        http.authorizeHttpRequests(registry -> registry
                // 配置请求授权 - 忽略请求
                .requestMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll()
                // 配置请求授权 - 其他请求
                .anyRequest().authenticated());

        // 关闭csrf
        http.csrf(AbstractHttpConfigurer::disable);

        // 配置资源服务器
        http.oauth2ResourceServer(configurer -> configurer
                .jwt(jwtConfigurer -> userDetailsService.ifPresent(service -> jwtConfigurer.jwtAuthenticationConverter(new WebJwtAuthenticationConverter(service)))));

        // 配置session管理
        http.sessionManagement(configurer -> configurer
                // 禁用session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 配置异常处理
        http.exceptionHandling(configurer -> configurer
                // 配置异常处理 - Bearer Token 认证入口点
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                // 配置异常处理 - Bearer Token 访问拒绝处理器
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));
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
                .map(serviceInstance -> StrUtil.format(SecurityConstants.URL_TEMPLATE, serviceInstance.getHost(), serviceInstance.getPort()))
                // 获取任意一个
                .findAny()
                // 获取不到则返回空字符串
                .orElse("");
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

}
