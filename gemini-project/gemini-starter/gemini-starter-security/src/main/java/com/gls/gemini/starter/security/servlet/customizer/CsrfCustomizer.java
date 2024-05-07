package com.gls.gemini.starter.security.servlet.customizer;

import com.gls.gemini.starter.security.constants.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CsrfCustomizer implements Customizer<CsrfConfigurer<HttpSecurity>> {
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(CsrfConfigurer<HttpSecurity> configurer) {
        if (!securityProperties.isCsrfEnabled()) {
            configurer.disable();
        }
    }
}
