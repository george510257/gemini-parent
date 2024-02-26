package com.gls.gemini.sdk.feishu.vo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取 tenant_access_token 响应
 */
@Data
public class TenantAccessTokenVo implements Serializable {
    /**
     * code : 错误码，非 0 取值表示失败
     */
    @JsonProperty("code")
    private Integer code;
    /**
     * msg : 错误信息
     */
    @JsonProperty("msg")
    private String msg;
    /**
     * tenant_access_token : 租户级别的 access_token
     */
    @JsonProperty("tenant_access_token")
    private String tenantAccessToken;
    /**
     * expire : tenant_access_token 的过期时间，单位为秒
     */
    @JsonProperty("expire")
    private Integer expire;
}
