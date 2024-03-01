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
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".security.ignore")
public class SecurityIgnoreProperties extends BaseProperties {
    /**
     * 忽略的 URL (静态资源，不通过过滤器链)
     */
    private String[] ignoreUrls = new String[]{};

    /**
     * 忽略的 URL (非静态资源，通过过滤器链)
     */
    private String[] ignorePatterns = new String[]{};
}
