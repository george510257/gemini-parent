package com.gls.gemini.starter.excel.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * excel配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".excel")
public class ExcelProperties extends BaseProperties {
    /**
     * 模板路径
     */
    private String templatePath;
}
