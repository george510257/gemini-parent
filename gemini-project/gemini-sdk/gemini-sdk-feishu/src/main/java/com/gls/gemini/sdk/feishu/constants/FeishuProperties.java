package com.gls.gemini.sdk.feishu.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = FeishuConstants.BASE_PROPERTIES_PREFIX)
public class FeishuProperties extends BaseProperties {

}
