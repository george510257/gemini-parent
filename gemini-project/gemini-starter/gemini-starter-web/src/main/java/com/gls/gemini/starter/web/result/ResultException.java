package com.gls.gemini.starter.web.result;

import com.gls.gemini.common.core.support.IEnums;
import lombok.Getter;

@Getter
public class ResultException extends RuntimeException {

    private final Integer code;

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

    public ResultException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = cause.getMessage();
    }

    public ResultException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public ResultException(IEnums enums) {
        this(enums.getCode(), enums.getMessage());
    }

    public ResultException(IEnums enums, Throwable cause) {
        this(enums.getCode(), enums.getMessage(), cause);
    }

    public ResultException(IEnums enums, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(enums.getCode(), enums.getMessage(), cause, enableSuppression, writableStackTrace);
    }

}
