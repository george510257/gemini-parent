package com.gls.gemini.sdk.feishu.support;

import com.gls.gemini.sdk.feishu.constants.FeishuProperties;
import com.gls.gemini.sdk.feishu.feign.AuthFeign;
import com.gls.gemini.sdk.feishu.vo.auth.AppTicketVo;
import com.gls.gemini.sdk.feishu.vo.auth.TenantKeyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {
    @Resource
    private AuthFeign authFeign;
    @Resource
    private FeishuProperties feishuProperties;

    /**
     * 获取租户访问令牌
     *
     * @return 租户访问令牌
     */
    public String getTenantAccessToken() {
        // 自建应用
        if (FeishuProperties.Mode.CUSTOM.equals(feishuProperties.getMode())) {
            return authFeign.getTenantAccessToken(feishuProperties.getApp()).getTenantAccessToken();
        }
        // 商店应用
        TenantKeyVo tenantKeyVo = new TenantKeyVo();
        tenantKeyVo.setTenantKey(feishuProperties.getTenantKey());
        tenantKeyVo.setAppAccessToken(getAppAccessToken());
        return authFeign.getTenantAccessToken(tenantKeyVo).getTenantAccessToken();
    }

    /**
     * 获取应用访问令牌
     *
     * @return 应用访问令牌
     */
    public String getAppAccessToken() {
        // 自建应用
        if (FeishuProperties.Mode.CUSTOM.equals(feishuProperties.getMode())) {
            return authFeign.getAppAccessToken(feishuProperties.getApp()).getAppAccessToken();
        }
        // 商店应用
        AppTicketVo appTicketVo = new AppTicketVo();
        appTicketVo.setAppId(feishuProperties.getApp().getAppId());
        appTicketVo.setAppSecret(feishuProperties.getApp().getAppSecret());
        appTicketVo.setAppTicket(getAppTicket());
        return authFeign.getAppAccessToken(appTicketVo).getAppAccessToken();
    }

    /**
     * 获取应用凭证
     *
     * @return 应用凭证
     */
    private String getAppTicket() {
        // 商店应用
        return authFeign.resendAppTicket(feishuProperties.getApp()).getData();
    }
}
