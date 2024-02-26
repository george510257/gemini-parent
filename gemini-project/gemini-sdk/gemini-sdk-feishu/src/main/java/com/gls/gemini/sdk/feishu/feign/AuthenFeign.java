package com.gls.gemini.sdk.feishu.feign;

import com.gls.gemini.sdk.feishu.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feishu", contextId = "authen", path = "/authen")
public interface AuthenFeign {

    /**
     * 获取 user_access_token
     *
     * @param codeVo        请求参数
     * @param authorization 请求头 Authorization 参数 app_access_token 格式为 "Bearer app_access_token"
     * @return 返回参数 user_access_token
     */
    @PostMapping(value = "/v1/oidc/access_token")
    ResultVo<UserAccessTokenVo> getUserAccessToken(@RequestBody CodeVo codeVo, @RequestHeader("Authorization") String authorization);

    /**
     * 刷新 user_access_token
     *
     * @param refreshTokenVo 请求参数
     * @param authorization  请求头 Authorization 参数 app_access_token 格式为 "Bearer app_access_token"
     */
    @PostMapping(value = "/v1/oidc/refresh_access_token")
    ResultVo<UserAccessTokenVo> refreshUserAccessToken(@RequestBody RefreshTokenVo refreshTokenVo, @RequestHeader("Authorization") String authorization);

    /**
     * 获取登录用户信息
     *
     * @param authorization 请求头 Authorization 参数 user_access_token 格式为 "Bearer user_access_token"
     */
    @GetMapping(value = "/v1/user_info")
    ResultVo<UserInfoVo> getUserInfo(@RequestHeader("Authorization") String authorization);

    /**
     * 获取登录预授权码
     */
    @Deprecated
    @GetMapping(value = "/v1/index")
    String getIndex();

    /**
     * 获取 user_access_token
     */
    @Deprecated
    @PostMapping(value = "/v1/access_token")
    String getAccessToken();

    /**
     * 刷新 user_access_token
     */
    @Deprecated
    @PostMapping(value = "/v1/refresh_access_token")
    String refreshAccessToken();
}
