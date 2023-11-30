package com.gls.gemini.starter.core.properties;

import com.gls.gemini.common.core.constant.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.PROPERTIES_PREFIX_BASE + ".snowflake")
public class SnowflakeProperties extends BaseProperties {
    /**
     * 最大的机器节点ID
     */
    private Long maxWorkerId;
    /**
     * 最大的中心ID
     */
    private Long maxDataCenterId;
}
