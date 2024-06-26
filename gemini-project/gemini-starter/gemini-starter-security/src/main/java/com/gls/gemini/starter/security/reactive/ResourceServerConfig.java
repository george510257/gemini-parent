package com.gls.gemini.starter.security.reactive;

import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityProperties;
import com.gls.gemini.starter.security.reactive.customizer.AuthorizeExchangeCustomizer;
import com.gls.gemini.starter.security.reactive.customizer.CsrfCustomizer;
import com.gls.gemini.starter.security.reactive.customizer.ExceptionHandlingCustomizer;
import com.gls.gemini.starter.security.reactive.customizer.OAuth2ResourceServerCustomizer;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ResourceServerConfig {
    @Resource
    private ServerHttpSecurity serverHttpSecurity;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private AuthorizeExchangeCustomizer authorizeExchangeCustomizer;
    @Resource
    private CsrfCustomizer csrfCustomizer;
    @Resource
    private OAuth2ResourceServerCustomizer oauth2ResourceServerCustomizer;
    @Resource
    private ExceptionHandlingCustomizer exceptionHandlingCustomizer;
    @Resource
    private SecurityProperties securityProperties;

    @Bean
    @Order
    public SecurityWebFilterChain defaultSecurityFilterChain() {
        // 配置请求授权
        serverHttpSecurity.authorizeExchange(authorizeExchangeCustomizer);
        // 关闭csrf
        serverHttpSecurity.csrf(csrfCustomizer);
        // 配置资源服务器
        serverHttpSecurity.oauth2ResourceServer(oauth2ResourceServerCustomizer);
        // 配置异常处理
        serverHttpSecurity.exceptionHandling(exceptionHandlingCustomizer);
        // 返回http安全
        return serverHttpSecurity.build();
    }

    /**
     * jwt解码器
     *
     * @return jwt解码器
     */
    @Bean
    @ConditionalOnMissingBean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
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
                .orElse(securityProperties.getJwkSetUri());
        return new NimbusReactiveJwtDecoder(jwkSetUri);
    }

}
