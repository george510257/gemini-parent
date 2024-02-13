package com.gls.gemini.starter.excel.config;

import com.gls.gemini.starter.excel.constants.ExcelProperties;
import com.gls.gemini.starter.excel.support.ExcelRequestResolver;
import com.gls.gemini.starter.excel.support.ExcelResponseHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExcelConfig {

    @Resource
    private ExcelProperties excelProperties;

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void init() {
        // 添加自定义的Excel返回值处理器
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<>();
        newReturnValueHandlers.add(new ExcelResponseHandler(excelProperties));
        if (returnValueHandlers != null) {
            newReturnValueHandlers.addAll(returnValueHandlers);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(newReturnValueHandlers);

        // 添加自定义的Excel参数解析器
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>();
        newArgumentResolvers.add(new ExcelRequestResolver());
        if (argumentResolvers != null) {
            newArgumentResolvers.addAll(argumentResolvers);
        }
        requestMappingHandlerAdapter.setArgumentResolvers(newArgumentResolvers);
    }
}
