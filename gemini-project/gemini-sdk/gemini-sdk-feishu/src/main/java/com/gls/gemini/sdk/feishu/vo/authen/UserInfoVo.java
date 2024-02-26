package com.gls.gemini.sdk.feishu.vo.authen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
public class UserInfoVo implements Serializable {
    /**
     * 用户姓名
     */
    @JsonProperty("name")
    private String name;
    /**
     * 用户英文名
     */
    @JsonProperty("en_name")
    private String enName;
    /**
     * 用户头像
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /**
     * 用户头像 72x72
     */
    @JsonProperty("avatar_thumb")
    private String avatarThumb;
    /**
     * 用户头像 240x240
     */
    @JsonProperty("avatar_middle")
    private String avatarMiddle;
    /**
     * 用户头像 640x640
     */
    @JsonProperty("avatar_big")
    private String avatarBig;
    /**
     * 用户在应用内的唯一标识
     */
    @JsonProperty("open_id")
    private String openId;
    /**
     * 用户对ISV的唯一标识, 对于同一个ISV, 用户的所有应用共用一个union_id
     */
    @JsonProperty("union_id")
    private String unionId;
    /**
     * 用户邮箱
     */
    @JsonProperty("email")
    private String email;
    /**
     * 企业邮箱
     */
    @JsonProperty("enterprise_email")
    private String enterpriseEmail;
    /**
     * 用户id
     */
    @JsonProperty("user_id")
    private String userId;
    /**
     * 用户手机号
     */
    @JsonProperty("mobile")
    private String mobile;
    /**
     * 当前企业标识
     */
    @JsonProperty("tenant_key")
    private String tenantKey;
    /**
     * 用户工号
     */
    @JsonProperty("employee_no")
    private String employeeNo;
}
