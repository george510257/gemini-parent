package com.gls.gemini.starter.openapi.properties.servers;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * 服务器变量属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerVariableProperties extends BaseProperties {
    /**
     * 枚举
     */
    @NestedConfigurationProperty
    private List<String> enums = null;
    /**
     * 默认值
     */
    private String defaultValue = null;
    /**
     * 描述
     */
    private String description = null;
}
