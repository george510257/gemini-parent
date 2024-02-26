package com.gls.gemini.sdk.feishu.vo.authen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshTokenVo implements Serializable {
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
