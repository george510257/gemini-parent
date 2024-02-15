package com.gls.gemini.common.core.domain;

import com.gls.gemini.common.core.interfaces.IEnums;
import lombok.Data;

/**
 * 返回结果
 *
 * @param <T> 返回数据类型
 * @author george
 */
@Data
public class Result<T> implements IEnums {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 跟踪ID
     */
    private String traceId;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
    }

    public Result(IEnums enums) {
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    public Result(IEnums enums, T data) {
        this.code = enums.getCode();
        this.message = enums.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}