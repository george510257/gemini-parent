package com.gls.gemini.starter.openapi.properties.info;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 许可证属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LicenseProperties extends BaseProperties {
    /**
     * 名称
     */
    private String name = null;
    /**
     * URL
     */
    private String url = null;
    /**
     * 标识符
     */
    private String identifier = null;
}
