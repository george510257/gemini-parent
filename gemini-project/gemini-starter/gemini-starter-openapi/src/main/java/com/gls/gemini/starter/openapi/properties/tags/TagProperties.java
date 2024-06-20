package com.gls.gemini.starter.openapi.properties.tags;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.starter.openapi.properties.ExternalDocumentationProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagProperties extends BaseProperties {
    /**
     * 名称
     */
    private String name = null;
    /**
     * 描述
     */
    private String description = null;
    /**
     * 外部文档
     */
    private ExternalDocumentationProperties externalDocs = null;
}
