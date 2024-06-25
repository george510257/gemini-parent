package com.gls.gemini.starter.openapi.converter;

import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.starter.openapi.properties.OpenApiProperties;
import com.gls.gemini.starter.openapi.properties.servers.ServerProperties;
import com.gls.gemini.starter.openapi.properties.servers.ServerVariableProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OpenApi转换器
 */
@Slf4j
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class OpenApiConverter implements BaseConverter<OpenApiProperties, OpenAPI> {
    @Mappings({
            @Mapping(target = "openapi", source = "openapi"),
            @Mapping(target = "info", source = "info"),
            @Mapping(target = "externalDocs", source = "externalDocs"),
            @Mapping(target = "servers", expression = "java(convertServerList(openApiProperties.getServers()))"),
            @Mapping(target = "security", expression = "java(convertSecurityList(openApiProperties.getSecurity()))"),
            @Mapping(target = "tags", source = "tags"),
            @Mapping(target = "paths", source = "paths"),
            @Mapping(target = "components", source = "components"),
            @Mapping(target = "extensions", source = "extensions")
    })
    @Override
    public abstract OpenAPI convert(OpenApiProperties openApiProperties);

    /**
     * 转换服务器列表
     *
     * @param serverProperties 服务器属性列表
     * @return 服务器列表
     */
    protected List<Server> convertServerList(List<ServerProperties> serverProperties) {
        return serverProperties.stream().map(this::convertServer).collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(target = "url", source = "url"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "variables", expression = "java(convertVariables(serverProperties.getVariables()))"),
            @Mapping(target = "extensions", source = "extensions")
    })
    public abstract Server convertServer(ServerProperties serverProperties);

    /**
     * 转换服务器变量
     *
     * @param serverVariableProperties 服务器变量属性
     * @return 服务器变量
     */
    protected ServerVariables convertVariables(Map<String, ServerVariableProperties> serverVariableProperties) {
        ServerVariables serverVariables = new ServerVariables();
        serverVariableProperties.forEach((key, value) -> serverVariables.addServerVariable(key, convertVariable(value)));
        return serverVariables;
    }

    /**
     * 转换服务器变量
     *
     * @param serverVariableProperties 服务器变量属性
     * @return 服务器变量
     */
    private ServerVariable convertVariable(ServerVariableProperties serverVariableProperties) {
        ServerVariable serverVariable = new ServerVariable();
        serverVariable.setEnum(serverVariableProperties.getEnums());
        serverVariable.setDefault(serverVariableProperties.getDefaultValue());
        serverVariable.setDescription(serverVariableProperties.getDescription());
        serverVariable.setExtensions(serverVariableProperties.getExtensions());
        return serverVariable;
    }

    /**
     * 转换安全需求列表
     *
     * @param securityProperties 安全属性列表
     * @return 安全需求列表
     */
    protected List<SecurityRequirement> convertSecurityList(List<Map<String, List<String>>> securityProperties) {
        return securityProperties.stream().map(this::convertSecurity).collect(Collectors.toList());
    }

    /**
     * 转换安全需求
     *
     * @param stringListMap 安全属性
     * @return 安全需求
     */
    private SecurityRequirement convertSecurity(Map<String, List<String>> stringListMap) {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        stringListMap.forEach(securityRequirement::addList);
        return securityRequirement;
    }

    @Override
    public OpenAPI convertCopy(OpenApiProperties openApiProperties, OpenAPI openAPI) {
        log.warn("OpenApiConverter.convertCopy方法未实现");
        throw new UnsupportedOperationException();
    }

    @Override
    public OpenApiProperties reverse(OpenAPI openAPI) {
        log.warn("OpenApiConverter.reverse方法未实现");
        throw new UnsupportedOperationException();
    }

    @Override
    public OpenApiProperties reverseCopy(OpenAPI openAPI, OpenApiProperties openApiProperties) {
        log.warn("OpenApiConverter.reverseCopy方法未实现");
        throw new UnsupportedOperationException();
    }
}
