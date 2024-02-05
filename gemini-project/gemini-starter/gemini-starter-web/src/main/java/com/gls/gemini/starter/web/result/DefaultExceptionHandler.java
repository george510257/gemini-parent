package com.gls.gemini.starter.web.result;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.common.core.domain.Result;
import com.gls.gemini.common.core.enums.ResultEnums;
import com.gls.gemini.common.core.exception.BusinessException;
import com.gls.gemini.common.core.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = CommonConstants.BASE_PACKAGE_PREFIX)
public class DefaultExceptionHandler {

    /**
     * 返回异常处理
     *
     * @param e 异常
     * @return 返回值
     */
    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> resultExceptionHandler(ResultException e) {
        log.error("ResultException: {}", e.getMessage(), e);
        return ResultEnums.FAILED
                .getResult(ExceptionUtil.stacktraceToString(e))
                .setCode(e.getCode())
                .setMessage(e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e 异常
     * @return 返回值
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);
        return ResultEnums.BUSINESS_EXCEPTION
                .getResult(ExceptionUtil.stacktraceToString(e))
                .setMessage(e.getMessage());
    }

    /**
     * 运行时异常处理
     *
     * @param e 异常
     * @return 返回值
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResultEnums.INTERNAL_SERVER_ERROR
                .getResult(ExceptionUtil.stacktraceToString(e))
                .setMessage(e.getMessage());
    }

    /**
     * 异常处理
     *
     * @param e 异常
     * @return 返回值
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResultEnums.INTERNAL_SERVER_ERROR
                .getResult(ExceptionUtil.stacktraceToString(e))
                .setMessage(e.getMessage());
    }
}
