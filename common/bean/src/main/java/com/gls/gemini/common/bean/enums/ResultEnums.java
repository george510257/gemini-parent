package com.gls.gemini.common.bean.enums;

import com.gls.gemini.common.bean.domain.Result;
import com.gls.gemini.common.core.base.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 返回结果枚举
 */
@Getter
@RequiredArgsConstructor
public enum ResultEnums implements BaseEnums<ResultEnums> {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAILED(500, "失败"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1000, "未知错误"),
    /**
     * 参数错误
     */
    PARAM_ERROR(1001, "参数错误"),
    /**
     * 参数为空
     */
    PARAM_IS_NULL(1002, "参数为空"),
    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    /**
     * 服务异常
     */
    SERVICE_ERROR(1005, "服务异常"),
    /**
     * 业务异常
     */
    BUSINESS_ERROR(1006, "业务异常"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(1007, "系统异常"),
    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(1008, "系统繁忙"),
    /**
     * 请求超时
     */
    TIMEOUT_ERROR(1009, "请求超时"),
    /**
     * 请求方法错误
     */
    HTTP_METHOD_ERROR(1010, "请求方法错误"),
    /**
     * 请求类型错误
     */
    HTTP_MEDIA_TYPE_ERROR(1011, "请求类型错误"),
    /**
     * 服务器不可用
     */
    SERVER_UNAVAILABLE(1012, "服务器不可用"),
    /**
     * 服务器未找到
     */
    SERVER_NOT_FOUND(1013, "服务器未找到"),
    /**
     * 服务器未接受
     */
    SERVER_NOT_ACCEPTABLE(1014, "服务器未接受"),
    /**
     * 服务器内部错误
     */
    SERVER_INTERNAL_ERROR(1015, "服务器内部错误"),
    /**
     * 服务器网关错误
     */
    SERVER_GATEWAY_ERROR(1016, "服务器网关错误"),
    /**
     * 服务器网关超时
     */
    SERVER_GATEWAY_TIMEOUT(1017, "服务器网关超时"),
    /**
     * 服务器网关不支持
     */
    SERVER_GATEWAY_NOT_SUPPORTED(1018, "服务器网关不支持");
    /**
     * 状态码
     */
    private final Integer key;
    /**
     * 状态信息
     */
    private final String value;

    /**
     * 返回结果
     */
    public Result<Object> getResult() {
        return new Result<>()
                .setCode(key)
                .setMessage(value);
    }

    /**
     * 返回结果
     *
     * @param data 返回数据
     * @param <T>  返回数据类型
     * @return 返回结果
     */
    public <T> Result<T> getResult(T data) {
        return new Result<T>()
                .setCode(key)
                .setMessage(value)
                .setData(data);
    }

}
