package com.gls.gemini.starter.web.result;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.gls.gemini.common.bean.domain.Result;
import com.gls.gemini.common.bean.enums.ClientTypeEnums;
import com.gls.gemini.common.bean.enums.ResultEnums;
import com.gls.gemini.common.bean.exception.ResultException;
import com.gls.gemini.common.core.constant.HeaderConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

/**
 * 返回值处理
 */
@Slf4j
@RestControllerAdvice
public class ResultHandler implements ResponseBodyAdvice<Object> {
    /**
     * 忽略的返回值类型
     */
    @Resource
    private ResultIgnoreProperties ignoreProperties;

    /**
     * 是否支持advice功能
     *
     * @param returnType    返回值类型
     * @param converterType 转换器类型
     * @return true 支持
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("resultHandler supports");
        // 返回值类型
        log.info("returnType:{}", returnType.getGenericParameterType().getClass().getSimpleName());
        // 返回值转换器类型
        log.info("converterType:{}", converterType.getSimpleName());
        if (CollUtil.isNotEmpty(ignoreProperties.getReturnType())) {
            // 判断是否包含
            return !ignoreProperties.getReturnType().contains(returnType.getGenericParameterType().getClass());
        }
        if (CollUtil.isNotEmpty(ignoreProperties.getConverterType())) {
            // 判断是否包含
            return !ignoreProperties.getConverterType().contains(converterType);
        }
        return true;
    }

    /**
     * 统一处理返回值
     *
     * @param body                  返回值
     * @param returnType            返回值类型
     * @param selectedContentType   选择的内容类型
     * @param selectedConverterType 选择的转换器类型
     * @param request               http请求
     * @param response              http响应
     * @return 返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("resultHandler beforeBodyWrite");

        // 判断是否时feign调用
        if (Optional.ofNullable(request.getHeaders().get(HeaderConstants.CLIENT_TYPE))
                .filter(list -> list.contains(ClientTypeEnums.FEIGN.toString())).isPresent()) {
            return body;
        }
        // 判断返回值类型是否为Result
        if (body instanceof Result) {
            return body;
        }
        // 判断返回值类型是否为String
        if (body instanceof String) {
            return body;
        }
        return ResultEnums.SUCCESS.getResult(body);
    }

    /**
     * 返回异常处理
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
}
