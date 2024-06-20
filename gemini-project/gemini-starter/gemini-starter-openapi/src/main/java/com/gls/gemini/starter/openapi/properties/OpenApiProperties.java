package com.gls.gemini.starter.openapi.properties;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.openapi.properties.info.InfoProperties;
import com.gls.gemini.starter.openapi.properties.servers.ServerProperties;
import com.gls.gemini.starter.openapi.properties.tags.TagProperties;
import io.swagger.v3.oas.models.PathItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenApi属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".open-api")
public class OpenApiProperties extends BaseProperties {
    /**
     * OpenApi版本
     */
    private String openapi = "3.0.1";
    /**
     * 信息
     */
    @NestedConfigurationProperty
    private InfoProperties info = new InfoProperties();
    /**
     * 外部文档
     */
    @NestedConfigurationProperty
    private ExternalDocumentationProperties externalDocs = new ExternalDocumentationProperties();
    /**
     * 服务器
     */
    @NestedConfigurationProperty
    private List<ServerProperties> servers = new ArrayList<>();
    /**
     * 安全
     */
    @NestedConfigurationProperty
    private List<Map<String, List<String>>> security = new ArrayList<>();
    /**
     * 标签
     */
    @NestedConfigurationProperty
    private List<TagProperties> tags = new ArrayList<>();
    /**
     * 路径
     */
    @NestedConfigurationProperty
    private Map<String, PathItem> paths = new HashMap<>();
    /**
     * 组件
     */
    @NestedConfigurationProperty
    private ComponentsProperties components = new ComponentsProperties();
}
