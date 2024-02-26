package com.gls.gemini.sdk.feishu.interceptor;

import com.gls.gemini.common.core.constant.HeaderConstants;
import com.gls.gemini.sdk.feishu.constants.FeishuConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.target(FeishuConstants.FEISHU_HOST);
        template.header(HeaderConstants.CONTENT_TYPE, HeaderConstants.APPLICATION_JSON_UTF8);
    }

}
