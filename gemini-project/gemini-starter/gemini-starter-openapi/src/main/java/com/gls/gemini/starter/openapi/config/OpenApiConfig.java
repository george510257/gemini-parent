package com.gls.gemini.starter.openapi.config;

import com.gls.gemini.starter.openapi.constants.OpenApiProperties;
import com.gls.gemini.starter.openapi.support.OpenApiConverter;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi配置
 */
@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI配置
     *
     * @param openApiProperties OpenApi配置
     * @param openApiConverter  OpenApi转换器
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI(OpenApiProperties openApiProperties, OpenApiConverter openApiConverter) {
        return openApiConverter.convert(openApiProperties);
    }
}
