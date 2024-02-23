package com.gls.gemini.sdk.feishu.feign;

import com.gls.gemini.sdk.feishu.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 */
@FeignClient(name = "feishu", contextId = "auth", path = "/auth")
public interface AuthFeign {

    /**
     * 自建应用获取 tenant_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping(value = "/v3/tenant_access_token/internal", consumes = "application/json;charset=UTF-8")
    TenantAccessTokenVo getTenantAccessToken(@RequestBody AppVo request);

    /**
     * 自建应用获取 app_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping(value = "/v3/app_access_token/internal", consumes = "application/json;charset=UTF-8")
    AppAccessTokenVo getAppAccessToken(@RequestBody AppVo request);

    /**
     * 商店应用获取 app_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping(value = "/v3/app_access_token", consumes = "application/json;charset=UTF-8")
    AppAccessTokenVo getAppAccessToken(@RequestBody AppTicketVo request);

    /**
     * 商店应用获取 tenant_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping(value = "/v3/tenant_access_token", consumes = "application/json;charset=UTF-8")
    TenantAccessTokenVo getTenantAccessToken(@RequestBody TenantKeyVo request);

    /**
     * 重新获取 app_ticket
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping(value = "/v3/app_ticket/resend", consumes = "application/json;charset=UTF-8")
    ResultVo<String> resendAppTicket(@RequestBody AppVo request);
}
