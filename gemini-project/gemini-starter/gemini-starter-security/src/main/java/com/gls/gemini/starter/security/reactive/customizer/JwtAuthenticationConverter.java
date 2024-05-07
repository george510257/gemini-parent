package com.gls.gemini.starter.security.reactive.customizer;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * jwt认证转换器
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnBean(ReactiveUserDetailsService.class)
public class JwtAuthenticationConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {
    /**
     * 用户详情服务
     */
    @Resource
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt jwt) {
        // 获取用户名
        String username = jwt.getSubject();
        // 创建用户名密码认证令牌
        Mono<UserDetails> userDetails = reactiveUserDetailsService.findByUsername(username);
        return userDetails.map(user -> UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), user.getAuthorities()));
    }
}
