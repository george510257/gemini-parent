package com.gls.gemini.sdk.feishu.feign;

import com.gls.gemini.sdk.feishu.vo.ResultVo;
import com.gls.gemini.sdk.feishu.vo.authen.CodeVo;
import com.gls.gemini.sdk.feishu.vo.authen.RefreshTokenVo;
import com.gls.gemini.sdk.feishu.vo.authen.UserAccessTokenVo;
import com.gls.gemini.sdk.feishu.vo.authen.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feishu", contextId = "authen", path = "/authen/v1")
public interface AuthenFeign {

    /**
     * 获取 user_access_token
     *
     * @param codeVo 请求参数
     * @return 返回参数 user_access_token
     */
    @PostMapping("/oidc/access_token")
    ResultVo<UserAccessTokenVo> getUserAccessToken(@RequestBody CodeVo codeVo);

    /**
     * 刷新 user_access_token
     *
     * @param refreshTokenVo 请求参数
     */
    @PostMapping("/oidc/refresh_access_token")
    ResultVo<UserAccessTokenVo> refreshUserAccessToken(@RequestBody RefreshTokenVo refreshTokenVo);

    /**
     * 获取登录用户信息
     */
    @GetMapping("/user_info")
    ResultVo<UserInfoVo> getUserInfo();
}
