package com.gls.gemini.sdk.feishu.vo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取 app_access_token 请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppTicketVo extends AppVo {

    /**
     * app_ticket : 平台定时推送给应用的临时凭证，通过事件监听机制获得。
     */
    @JsonProperty("app_ticket")
    private String appTicket;
}
