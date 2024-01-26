package com.gls.gemini.common.bean.enums;

import com.gls.gemini.common.bean.interfaces.IEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 客户端类型枚举
 *
 * @author george
 */
@Getter
@RequiredArgsConstructor
public enum ClientTypeEnums implements IEnums {
    /**
     * 未知
     */
    UNKNOWN(0, ""),
    /**
     * PC端
     */
    PC(1, "PC"),
    /**
     * H5(移动端)
     */
    H5(2, "H5"),
    /**
     * IOS(移动端)
     */
    IOS(3, "IOS"),
    /**
     * ANDROID(移动端)
     */
    ANDROID(4, "ANDROID"),
    /**
     * 微信小程序 (微信小程序)
     */
    WECHAT_MINI_PROGRAM(5, "WECHAT_MINI_PROGRAM"),
    /**
     * 飞书小程序 (飞书小程序)
     */
    FEISHU_MINI_PROGRAM(6, "FEISHU_MINI_PROGRAM"),
    /**
     * 钉钉小程序 (钉钉小程序)
     */
    DINGDING_MINI_PROGRAM(7, "DINGDING_MINI_PROGRAM"),
    /**
     * 第三方接口 (开放接口) 需要鉴权
     */
    OPEN_API(8, "OPEN_API"),
    /**
     * Feign接口 (内部调用) 不需要鉴权
     */
    FEIGN(9, "FEIGN");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String message;

    @Override
    public String toString() {
        return this.message;
    }
}
