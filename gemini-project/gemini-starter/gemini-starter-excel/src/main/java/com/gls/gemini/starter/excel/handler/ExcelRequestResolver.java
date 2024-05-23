package com.gls.gemini.starter.excel.handler;

import com.alibaba.excel.EasyExcel;
import com.gls.gemini.starter.excel.annotation.ExcelRequest;
import com.gls.gemini.starter.excel.listener.ListReadListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel请求解析器
 */
@Slf4j
@Component
@ConditionalOnBean(RequestMappingHandlerAdapter.class)
public class ExcelRequestResolver implements HandlerMethodArgumentResolver {
    /**
     * 请求映射处理适配器
     */
    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void init() {
        // 添加自定义的Excel参数解析器
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>();
        newArgumentResolvers.add(this);
        if (argumentResolvers != null) {
            newArgumentResolvers.addAll(argumentResolvers);
        }
        requestMappingHandlerAdapter.setArgumentResolvers(newArgumentResolvers);
    }

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

    /**
     * 解析参数
     *
     * @param parameter     方法参数
     * @param mavContainer  模型视图容器
     * @param webRequest    web请求
     * @param binderFactory 绑定工厂
     * @return 解析结果
     * @throws Exception 异常
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取参数类型
        Class<?> parameterType = parameter.getParameterType();
        // 如果不是List类型, 抛出异常
        if (!List.class.isAssignableFrom(parameterType)) {
            throw new IllegalArgumentException("Excel upload request resolver error, @ExcelRequest parameter is not List " + parameterType);
        }
        // 获取请求参数
        ExcelRequest excelRequest = parameter.getParameterAnnotation(ExcelRequest.class);
        assert excelRequest != null;
        Class<? extends ListReadListener> readListenerClass = excelRequest.readListener();
        ListReadListener<?> readListener = BeanUtils.instantiateClass(readListenerClass);
        log.info("Excel upload request resolver, readListener: {}", readListener);
        // 获取文件流
        MultipartRequest request = webRequest.getNativeRequest(MultipartRequest.class);
        assert request != null;
        MultipartFile file = request.getFile(excelRequest.fileName());
        assert file != null;
        InputStream inputStream = file.getInputStream();
        log.info("Excel upload request resolver, fileName: {}", file.getOriginalFilename());

        // 获取目标类型
        Class<?> target = ResolvableType.forMethodParameter(parameter).getGeneric(0).resolve();
        log.info("Excel upload request resolver, target: {}", target);

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
