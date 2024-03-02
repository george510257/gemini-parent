package com.gls.gemini.starter.security.config;

import cn.hutool.core.util.StrUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import com.gls.gemini.starter.security.converter.WebFluxJwtAuthenticationConverter;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Optional;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxResourceServerConfig {

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

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        // 配置请求授权
        http.authorizeExchange(spec -> spec
                // 忽略配置的url
                .pathMatchers(securityIgnoreProperties.getIgnoreUrls()).permitAll()
                // 忽略配置的url
                .pathMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll()
                // 其他请求需要认证
                .anyExchange().authenticated());
        // 关闭csrf
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        // 配置资源服务器
        http.oauth2ResourceServer(spec -> spec.jwt(jwtSpec ->
                // 配置jwt认证转换器
                userDetailsService.ifPresent(service -> jwtSpec.jwtAuthenticationConverter(new WebFluxJwtAuthenticationConverter(service)))));
        // 配置异常处理
        http.exceptionHandling(spec -> spec
                // 配置认证入口点
                .authenticationEntryPoint(new BearerTokenServerAuthenticationEntryPoint())
                // 配置拒绝访问处理
                .accessDeniedHandler(new BearerTokenServerAccessDeniedHandler()));
        // 返回http安全
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveJwtDecoder reactiveJwtDecoder(DiscoveryClient discoveryClient) {
        String jwkSetUri = discoveryClient.getServices().stream()
                // 获取uaa服务id
                .filter(serviceId -> serviceId.contains(CommonConstants.UAA_SERVICE_ID))
                // 获取uaa服务实例
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                // 获取uaa服务实例的uri
                .map(serviceInstance -> StrUtil.format(SecurityConstants.URL_TEMPLATE, serviceInstance.getHost(), serviceInstance.getPort()))
                // 获取任意一个
                .findAny()
                // 获取不到则返回空字符串
                .orElse("");
        return new NimbusReactiveJwtDecoder(jwkSetUri);
    }

}
