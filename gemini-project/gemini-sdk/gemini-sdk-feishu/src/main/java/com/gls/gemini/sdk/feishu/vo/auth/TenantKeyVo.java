package com.gls.gemini.sdk.feishu.vo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取 tenant_access_token 请求
 */
@Data
public class TenantKeyVo implements Serializable {
    /**
     * app_access_token : 应用级别的 access_token
     */
    @JsonProperty("app_access_token")
    private String appAccessToken;
    /**
     * tenant_key : 企业的唯一标识
     */
    @JsonProperty("tenant_key")
    private String tenantKey;
}
