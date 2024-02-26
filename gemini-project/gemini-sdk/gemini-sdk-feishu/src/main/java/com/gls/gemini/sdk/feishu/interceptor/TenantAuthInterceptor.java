package com.gls.gemini.sdk.feishu.interceptor;

import com.gls.gemini.common.core.constant.HeaderConstants;
import com.gls.gemini.sdk.feishu.support.AuthHelper;
import feign.RequestTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantAuthInterceptor extends DefaultInterceptor {
    @Resource
    private AuthHelper authHelper;

    @Override
    public void apply(RequestTemplate template) {
        super.apply(template);
        template.header(HeaderConstants.AUTHORIZATION, HeaderConstants.TOKEN_BEARER + authHelper.getTenantAccessToken());
    }
}
