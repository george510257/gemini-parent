package com.gls.gemini.starter.security.support;

import com.gls.gemini.starter.security.constants.SecurityIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebFluxSecurityHelper {

    /**
     * 安全忽略属性
     */
    private final SecurityIgnoreProperties securityIgnoreProperties;
    /**
     * 用户详情服务
     */
    private final UserDetailsService userDetailsService;

    public void authorizeExchangeCustomizer(ServerHttpSecurity.AuthorizeExchangeSpec spec) {
        spec.pathMatchers(securityIgnoreProperties.getIgnorePatterns()).permitAll()
                .anyExchange().authenticated();
    }

    public void csrfCustomizer(ServerHttpSecurity.CsrfSpec spec) {
        spec.disable();
    }

    public void oauth2ResourceServerCustomizer(ServerHttpSecurity.OAuth2ResourceServerSpec spec) {
        spec.jwt(this::jwtCustomizer);
    }

    private void jwtCustomizer(ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec spec) {
        spec.jwtAuthenticationConverter(this::jwtAuthenticationConverter);
    }

    private Mono<? extends AbstractAuthenticationToken> jwtAuthenticationConverter(Jwt jwt) {
        // 获取用户名
        String username = jwt.getSubject();
        // 创建用户名密码认证令牌
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Mono.just(UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    public void exceptionHandlingCustomizer(ServerHttpSecurity.ExceptionHandlingSpec spec) {
        // 配置异常处理 - Bearer Token 认证入口点
        spec.authenticationEntryPoint(new BearerTokenServerAuthenticationEntryPoint());
        // 配置异常处理 - Bearer Token 访问拒绝处理器
        spec.accessDeniedHandler(new BearerTokenServerAccessDeniedHandler());
    }
}
