package com.gls.gemini.starter.security.config;

import cn.hutool.core.util.StrUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.security.constants.SecurityConstants;
import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import com.gls.gemini.starter.security.support.WebFluxSecurityHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxResourceServerConfig {

    @Bean
    public WebFluxSecurityHelper securityHelper(SecurityIgnoreProperties securityIgnoreProperties,
                                                UserDetailsService userDetailsService) {
        return new WebFluxSecurityHelper(securityIgnoreProperties, userDetailsService);
    }

    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http, WebFluxSecurityHelper webFluxSecurityHelper) {
        // 配置请求授权
        http.authorizeExchange(webFluxSecurityHelper::authorizeExchangeCustomizer);
        // 关闭csrf
        http.csrf(webFluxSecurityHelper::csrfCustomizer);
        // 配置资源服务器
        http.oauth2ResourceServer(webFluxSecurityHelper::oauth2ResourceServerCustomizer);
        // 配置异常处理
        http.exceptionHandling(webFluxSecurityHelper::exceptionHandlingCustomizer);
        // 返回http安全
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveJwtDecoder reactiveJwtDecoder(DiscoveryClient discoveryClient) {
        String jwkSetUri = discoveryClient.getServices().stream()
                .filter(serviceId -> serviceId.contains(CommonConstants.UAA_SERVICE_ID))
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                .map(serviceInstance -> StrUtil.format(SecurityConstants.URL_TEMPLATE, serviceInstance.getHost(), serviceInstance.getPort()))
                .findAny()
                .orElse("");
        return new NimbusReactiveJwtDecoder(jwkSetUri);
    }

}
