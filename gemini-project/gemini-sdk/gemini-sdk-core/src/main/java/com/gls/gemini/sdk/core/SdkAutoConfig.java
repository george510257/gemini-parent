package com.gls.gemini.sdk.core;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gls.gemini.sdk.core.dto.RoleDto;
import com.gls.gemini.sdk.core.dto.UserDto;
import com.gls.gemini.sdk.core.jackson2.RoleDtoDeserializer;
import com.gls.gemini.sdk.core.jackson2.UserDtoDeserializer;
import com.gls.gemini.sdk.core.support.DefaultLoginTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan
public class SdkAutoConfig {

    /**
     * 登录用户模板
     *
     * @return 登录用户模板
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultLoginTemplate loginTemplate() {
        return new DefaultLoginTemplate();
    }

    @Bean
    @Order(200)
    public SimpleModule sdkCoreModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(RoleDto.class, new RoleDtoDeserializer());
        simpleModule.addDeserializer(UserDto.class, new UserDtoDeserializer());
        return simpleModule;
    }
}
