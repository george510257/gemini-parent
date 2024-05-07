package com.gls.gemini.starter.security.reactive.customizer;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

/**
 * CSRF定制器
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class CsrfCustomizer implements Customizer<ServerHttpSecurity.CsrfSpec> {
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(ServerHttpSecurity.CsrfSpec spec) {
        // 是否启用csrf
        if (!securityProperties.isCsrfEnabled()) {
            spec.disable();
        }
    }
}
