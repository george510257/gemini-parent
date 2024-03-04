package com.gls.gemini.starter.security.servlet;

import cn.hutool.core.util.StrUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
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
public class ResourceServerConfig {
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
        http.authorizeHttpRequests(this::authorizeHttpRequestsCustomizer);
        // 关闭csrf
        http.csrf(AbstractHttpConfigurer::disable);
        // 配置资源服务器
        http.oauth2ResourceServer(this::oauth2ResourceServerCustomizer);
        // 配置session管理
        http.sessionManagement(this::sessionManagementCustomizer);
        // 配置异常处理
        http.exceptionHandling(this::exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

    /**
     * 异常处理定制器
     *
     * @param configurer 异常处理定制器
     */
    private void exceptionHandlingCustomizer(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        // 配置异常处理 - Bearer Token 认证入口点
        configurer.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
        // 配置异常处理 - Bearer Token 访问拒绝处理器
        configurer.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
    }

    /**
     * session管理定制器
     *
     * @param configurer session管理定制器
     */
    private void sessionManagementCustomizer(SessionManagementConfigurer<HttpSecurity> configurer) {
        // 禁用session
        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 资源服务器定制器
     *
     * @param configurer 资源服务器定制器
     */
    private void oauth2ResourceServerCustomizer(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 配置jwt
        configurer.jwt(this::jwtCustomizer);
    }

    /**
     * jwt定制器
     *
     * @param configurer jwt定制器
     */
    private void jwtCustomizer(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer configurer) {
        // 配置jwt认证转换器
        configurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    /**
     * jwt认证转换器
     *
     * @return jwt认证转换器
     */
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return userDetailsService.<Converter<Jwt, ? extends AbstractAuthenticationToken>>map(detailsService -> (jwt) -> {
            // 获取用户名
            String username = jwt.getSubject();
            // 创建用户名密码认证令牌
            UserDetails userDetails = detailsService.loadUserByUsername(username);
            // 返回认证令牌
            return UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }).orElse(new JwtAuthenticationConverter());
    }

    /**
     * 请求授权定制器
     *
     * @param registry 请求授权定制器
     */
    private void authorizeHttpRequestsCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        // 配置请求授权 - 忽略请求
        registry.requestMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll();
        // 配置请求授权 - 其他请求
        registry.anyRequest().permitAll();
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
