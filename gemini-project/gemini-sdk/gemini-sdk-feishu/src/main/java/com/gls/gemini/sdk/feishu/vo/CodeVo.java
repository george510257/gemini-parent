package com.gls.gemini.sdk.feishu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取 code 请求
 */
@Data
public class CodeVo implements Serializable {
    /**
     * code : 临时授权码
     */
    @JsonProperty("code")
    private String code;
    /**
     * grant_type : 授权类型 "authorization_code"
     */
    @JsonProperty("grant_type")
    private String grantType;
}
