package com.gls.gemini.sdk.feishu.vo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取 tenant_access_token 请求
 */
@Data
public class AppVo implements Serializable {
    /**
     * app_id : 申请应用时分配的 app_id
     */
    @JsonProperty("app_id")
    private String appId;
    /**
     * app_secret : 申请应用时分配的 app_secret
     */
    @JsonProperty("app_secret")
    private String appSecret;
}
