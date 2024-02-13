package com.gls.gemini.starter.excel.support;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.gls.gemini.starter.excel.annotation.ExcelRequest;
import com.gls.gemini.starter.excel.listener.ListReadListener;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.InputStream;
import java.util.List;

/**
 * excel请求解析器
 */
@Component
public class ExcelRequestResolver implements HandlerMethodArgumentResolver {
    /**
     * 是否支持参数
     *
     * @param parameter 参数
     * @return 是否支持
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExcelRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取参数类型
        Class<?> parameterType = parameter.getParameterType();
        // 如果不是List类型, 抛出异常
        if (!parameterType.isAssignableFrom(List.class)) {
            throw new IllegalArgumentException("Excel upload request resolver error, @ExcelRequest parameter is not List " + parameterType);
        }
        // 获取请求参数
        ExcelRequest excelRequest = parameter.getParameterAnnotation(ExcelRequest.class);
        assert excelRequest != null;
        Class<? extends ListReadListener> readListenerClass = excelRequest.readListener();
        ListReadListener<?> readListener = SpringUtil.getBean(readListenerClass);

        // 获取文件流
        MultipartRequest request = webRequest.getNativeRequest(MultipartRequest.class);
        assert request != null;
        MultipartFile file = request.getFile(excelRequest.fileName());
        assert file != null;
        InputStream inputStream = file.getInputStream();

        // 获取目标类型
        Class<?> target = ResolvableType.forMethodParameter(parameter).getGeneric(0).resolve();

        EasyExcel.read(inputStream, target, readListener)
                .ignoreEmptyRow(excelRequest.ignoreEmptyRow())
                .sheet()
                .headRowNumber(excelRequest.headRowNumber())
                .doRead();

        // 创建绑定器
        WebDataBinder binder = binderFactory.createBinder(webRequest, readListener.getErrors(), "excel");
        ModelMap model = mavContainer.getModel();
        // 绑定参数
        model.put(BindingResult.MODEL_KEY_PREFIX + "excel", binder.getBindingResult());

        return readListener.getList();
    }
}
