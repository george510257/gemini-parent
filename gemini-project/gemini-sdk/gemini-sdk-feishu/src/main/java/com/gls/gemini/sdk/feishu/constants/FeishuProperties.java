package com.gls.gemini.sdk.feishu.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.sdk.feishu.vo.auth.AppVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 飞书配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = FeishuConstants.BASE_PROPERTIES_PREFIX)
public class FeishuProperties extends BaseProperties {
    /**
     * 应用模式
     */
    private Mode mode = Mode.CUSTOM;
    /**
     * 应用信息
     */
    @NestedConfigurationProperty
    private AppVo app;
    /**
     * 租户 key
     */
    private String tenantKey;

    public enum Mode {

        /**
         * 自建应用
         */
        CUSTOM,
        /**
         * 商店应用
         */
        STORE
    }
}
