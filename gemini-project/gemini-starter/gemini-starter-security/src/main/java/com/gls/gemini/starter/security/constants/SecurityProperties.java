package com.gls.gemini.starter.security.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全忽略属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".security")
public class SecurityProperties extends BaseProperties {
    /**
     * 忽略的 URL 地址
     */
    private String[] ignoreUrls;

    /**
     * csrf 是否启用
     */
    private boolean csrfEnabled = false;
    /**
     * jwkSetUri
     */
    private String jwkSetUri;
}
