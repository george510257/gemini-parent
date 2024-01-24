package com.gls.gemini.common.bean.result;

import com.gls.gemini.common.core.support.IEnums;
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
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
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
    public Result<Object> getResult() {
        return new Result<>()
                .setCode(code)
                .setMessage(message);
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
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
