package com.gls.gemini.common.bean.result;

import com.gls.gemini.common.core.base.BaseEnums;
import lombok.Getter;

/**
 * 返回结果异常
 */
@Getter
public class ResultException extends RuntimeException {

    /**
     * 异常码
     */
    private final Integer code;
    /**
     * 异常信息
     */
    private final String message;

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResultException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public ResultException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public ResultException(BaseEnums<?> enums) {
        super(enums.getValue());
        this.code = enums.getKey();
        this.message = enums.getValue();
    }

    public ResultException(BaseEnums<?> enums, Throwable cause) {
        super(enums.getValue(), cause);
        this.code = enums.getKey();
        this.message = enums.getValue();
    }

    public ResultException(BaseEnums<?> enums, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(enums.getValue(), cause, enableSuppression, writableStackTrace);
        this.code = enums.getKey();
        this.message = enums.getValue();
    }
}
