package com.gls.gemini.starter.openapi.properties;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外部文档属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalDocumentationProperties extends BaseProperties {
    /**
     * 描述
     */
    private String description = null;
    /**
     * URL
     */
    private String url = null;
}
