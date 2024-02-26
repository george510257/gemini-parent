package com.gls.gemini.sdk.feishu.feign;

import com.gls.gemini.sdk.feishu.interceptor.AuthInterceptor;
import com.gls.gemini.sdk.feishu.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 认证相关接口
 */
@FeignClient(name = "feishu", contextId = "auth", path = "/auth/v3", configuration = AuthInterceptor.class)
public interface AuthFeign {

    /**
     * 自建应用获取 tenant_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping("/tenant_access_token/internal")
    TenantAccessTokenVo getTenantAccessToken(@RequestBody AppVo request);

    /**
     * 自建应用获取 app_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping("/app_access_token/internal")
    AppAccessTokenVo getAppAccessToken(@RequestBody AppVo request);

    /**
     * 商店应用获取 app_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping("/app_access_token")
    AppAccessTokenVo getAppAccessToken(@RequestBody AppTicketVo request);

    /**
     * 商店应用获取 tenant_access_token
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping("/tenant_access_token")
    TenantAccessTokenVo getTenantAccessToken(@RequestBody TenantKeyVo request);

    /**
     * 重新获取 app_ticket
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @PostMapping("/app_ticket/resend")
    ResultVo<String> resendAppTicket(@RequestBody AppVo request);
}
