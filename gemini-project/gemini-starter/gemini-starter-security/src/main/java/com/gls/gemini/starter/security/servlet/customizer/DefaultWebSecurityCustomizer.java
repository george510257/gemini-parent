package com.gls.gemini.starter.security.servlet.customizer;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DefaultWebSecurityCustomizer implements WebSecurityCustomizer {
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(WebSecurity web) {
        if (securityProperties.getIgnoreUrls() != null) {
            web.ignoring().requestMatchers(securityProperties.getIgnoreUrls());
        }
    }
}
