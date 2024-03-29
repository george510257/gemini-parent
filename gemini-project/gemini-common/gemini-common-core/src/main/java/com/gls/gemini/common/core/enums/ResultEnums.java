package com.gls.gemini.common.core.enums;

import com.gls.gemini.common.core.domain.Result;
import com.gls.gemini.common.core.interfaces.IEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 返回结果枚举
 *
 * @author george
 */
@Getter
@RequiredArgsConstructor
public enum ResultEnums implements IEnums {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAILED(500, "失败"),
    /**
     * 未登录
     */
    UNAUTHORIZED(401, "未登录"),
    /**
     * 未授权
     */
    FORBIDDEN(403, "未授权"),
    /**
     * 未找到
     */
    NOT_FOUND(404, "未找到"),
    /**
     * 业务异常
     */
    BUSINESS_EXCEPTION(5001, "业务异常"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(5002, "服务器内部错误"),
    ;
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String message;

    /**
     * 返回结果
     */
    public <T> Result<T> getResult() {
        return new Result<>(this);
    }

    /**
     * 返回结果
     *
     * @param data 返回数据
     * @param <T>  返回数据类型
     * @return 返回结果
     */
    public <T> Result<T> getResult(T data) {
        return new Result<>(this, data);
    }

}
