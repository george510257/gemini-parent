package com.gls.gemini.sdk.feishu.interceptor;

import com.gls.gemini.sdk.feishu.constants.FeishuConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.target(FeishuConstants.FEISHU_HOST);
        template.header("Content-Type", "application/json; charset=utf-8");
    }
}
