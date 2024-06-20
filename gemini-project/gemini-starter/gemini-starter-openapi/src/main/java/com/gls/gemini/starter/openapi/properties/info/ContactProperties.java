package com.gls.gemini.starter.openapi.properties.info;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 联系人属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactProperties extends BaseProperties {
    /**
     * 名称
     */
    private String name = null;
    /**
     * URL
     */
    private String url = null;
    /**
     * 邮箱
     */
    private String email = null;
}
