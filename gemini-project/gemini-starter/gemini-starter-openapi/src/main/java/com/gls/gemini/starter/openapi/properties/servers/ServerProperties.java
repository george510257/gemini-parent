package com.gls.gemini.starter.openapi.properties.servers;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * 服务器属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerProperties extends BaseProperties {
    /**
     * URL
     */
    private String url = null;
    /**
     * 描述
     */
    private String description = null;
    /**
     * 变量
     */
    @NestedConfigurationProperty
    private Map<String, ServerVariableProperties> variables = null;
}
