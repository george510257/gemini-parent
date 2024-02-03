package com.gls.gemini.starter.openapi.support;

import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.starter.openapi.constants.OpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * OpenApi转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OpenApiConverter extends BaseConverter<OpenApiProperties, OpenAPI> {
}
