package com.gls.gemini.starter.openapi.properties.info;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 信息属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoProperties extends BaseProperties {
    /**
     * 标题
     */
    private String title = null;
    /**
     * 描述
     */
    private String description = null;
    /**
     * 服务条款
     */
    private String termsOfService = null;
    /**
     * 联系人
     */
    @NestedConfigurationProperty
    private ContactProperties contact = null;
    /**
     * 许可证
     */
    @NestedConfigurationProperty
    private LicenseProperties license = null;
    /**
     * 版本
     */
    private String version = null;
    /**
     * 摘要
     */
    private String summary = null;
}
