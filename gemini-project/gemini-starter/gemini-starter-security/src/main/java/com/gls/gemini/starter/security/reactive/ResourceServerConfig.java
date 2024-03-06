package com.gls.gemini.starter.security.reactive;

import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
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
    private Optional<ReactiveUserDetailsService> userDetailsService;

    @Bean
    @Order
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        // 配置请求授权
        http.authorizeExchange(this::authorizeExchangeCustomizer);
        // 关闭csrf
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        // 配置资源服务器
        http.oauth2ResourceServer(this::oauth2ResourceServerCustomizer);
        // 配置异常处理
        http.exceptionHandling(this::exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

    /**
     * 配置异常处理
     *
     * @param spec 异常处理配置
     */
    private void exceptionHandlingCustomizer(ServerHttpSecurity.ExceptionHandlingSpec spec) {
        // 配置认证入口点
        spec.authenticationEntryPoint(new BearerTokenServerAuthenticationEntryPoint());
        // 配置拒绝访问处理
        spec.accessDeniedHandler(new BearerTokenServerAccessDeniedHandler());
    }

    /**
     * 配置资源服务器
     *
     * @param spec 资源服务器配置
     */
    private void oauth2ResourceServerCustomizer(ServerHttpSecurity.OAuth2ResourceServerSpec spec) {
        // 配置jwt
        spec.jwt(this::jwtCustomizer);
    }

    /**
     * 配置jwt
     *
     * @param spec jwt配置
     */
    private void jwtCustomizer(ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec spec) {
        // 配置jwt认证转换器
        spec.jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    /**
     * jwt认证转换器
     *
     * @return jwt认证转换器
     */
    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return userDetailsService.<Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>>map(detailsService -> (jwt) -> {
            // 获取用户名
            String username = jwt.getSubject();
            // 创建用户名密码认证令牌
            Mono<UserDetails> userDetails = detailsService.findByUsername(username);
            // 返回认证令牌
            return userDetails.map(user -> UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), user.getAuthorities()));
        }).orElse(new ReactiveJwtAuthenticationConverter());
    }

    /**
     * 配置请求授权
     *
     * @param spec 请求授权配置
     */
    private void authorizeExchangeCustomizer(ServerHttpSecurity.AuthorizeExchangeSpec spec) {
        // 忽略配置的url
        spec.pathMatchers(securityIgnoreProperties.getIgnoreUrls()).permitAll();
        // 忽略配置的url
        spec.pathMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll();
        // 其他请求需要认证
        spec.anyExchange().authenticated();
    }

    /**
     * jwt解码器
     *
     * @param discoveryClient 服务发现客户端
     * @return jwt解码器
     */
    @Bean
    @ConditionalOnMissingBean
    public ReactiveJwtDecoder reactiveJwtDecoder(DiscoveryClient discoveryClient) {
        String jwkSetUri = discoveryClient.getServices().stream()
                // 获取uaa服务id
                .filter(serviceId -> serviceId.contains(CommonConstants.UAA_SERVICE_ID))
                // 获取uaa服务实例
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                // 获取uaa服务实例的uri
                .map(serviceInstance -> serviceInstance.getUri().toString() + SecurityConstants.JWK_SET_ENDPOINT)
                // 获取任意一个
                .findAny()
                // 获取不到则返回空字符串
                .orElse("");
        return new NimbusReactiveJwtDecoder(jwkSetUri);
    }

}
