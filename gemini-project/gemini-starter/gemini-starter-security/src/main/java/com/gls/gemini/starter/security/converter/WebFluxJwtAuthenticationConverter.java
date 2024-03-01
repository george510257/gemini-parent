package com.gls.gemini.starter.security.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebFluxJwtAuthenticationConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    private final UserDetailsService userDetailsService;

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt jwt) {
        // 获取用户名
        String username = jwt.getSubject();
        // 创建用户名密码认证令牌
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Mono.just(UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
