package com.gls.gemini.starter.excel.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.gls.gemini.starter.excel.annotation.ExcelResponse;
import com.gls.gemini.starter.excel.annotation.ExcelSheet;
import com.gls.gemini.starter.excel.constants.ExcelProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel响应处理器
 */
@Slf4j
@Component
@ConditionalOnBean(RequestMappingHandlerAdapter.class)
public class ExcelResponseHandler implements HandlerMethodReturnValueHandler {
    /**
     * Excel配置
     */
    @Resource
    private ExcelProperties excelProperties;
    /**
     * 请求映射处理适配器
     */
    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    /**
     * 转换器列表
     */
    @Resource
    private List<Converter<?>> converters;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        // 添加自定义的Excel返回值处理器
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<>();
        newReturnValueHandlers.add(this);
        if (returnValueHandlers != null) {
            newReturnValueHandlers.addAll(returnValueHandlers);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(newReturnValueHandlers);
    }

    /**
     * 是否支持返回值类型
     *
     * @param returnType 返回值类型
     * @return 是否支持
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelResponse.class);
    }

    /**
     * 处理返回值
     *
     * @param returnValue  返回值
     * @param returnType   返回值类型
     * @param mavContainer 模型视图容器
     * @param webRequest   web请求
     * @throws Exception 异常
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 设置请求已处理
        mavContainer.setRequestHandled(true);
        // 获取返回值类型
        Class<?> parameterType = returnType.getParameterType();
        // 如果不是List类型, 抛出异常
        if (!List.class.isAssignableFrom(parameterType)) {
            throw new IllegalArgumentException("Excel download response handler error, @ExcelResponse return value is not List " + parameterType);
        }
        List<?> list = (List<?>) returnValue;

        // 获取请求参数
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
        assert excelResponse != null;

        // 获取响应参数
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        assert response != null;
        // 设置响应参数
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(excelResponse.fileName(), StandardCharsets.UTF_8) + excelResponse.excelType().getValue());

        // 导出excel
        ExcelWriter excelWriter = getExcelWriter(response, excelResponse);
        List<WriteSheet> sheets = getWriteSheetList(list, excelResponse);
        // 写入数据
        if (sheets.size() > 1) {
            manySheetWrite((List<List<?>>) list, excelWriter, sheets, excelResponse.autoFill());
        } else {
            singleSheetWrite(list, excelWriter, sheets.getFirst(), excelResponse.autoFill());
        }
        // 关闭流
        excelWriter.finish();

    }

    /**
     * 多sheet写出
     *
     * @param returnValue 返回值
     * @param excelWriter excelWriter
     * @param sheets      sheets
     * @param autoFill    是否自动填充
     */
    private void manySheetWrite(List<List<?>> returnValue, ExcelWriter excelWriter, List<WriteSheet> sheets, boolean autoFill) {
        sheets.forEach(sheet -> singleSheetWrite(returnValue.get(sheets.indexOf(sheet)), excelWriter, sheet, autoFill));
    }

    /**
     * 单sheet写出
     *
     * @param returnValue 返回值
     * @param excelWriter excelWriter
     * @param writeSheet  writeSheet
     * @param autoFill    是否自动填充
     */
    private void singleSheetWrite(List<?> returnValue, ExcelWriter excelWriter, WriteSheet writeSheet, boolean autoFill) {
        if (autoFill) {
            excelWriter.fill(returnValue, writeSheet);
        } else {
            excelWriter.write(returnValue, writeSheet);
        }
    }

    /**
     * 获取WriteSheet列表
     *
     * @param returnValue
     * @param excelResponse excelResponse
     * @return WriteSheet列表
     */
    private List<WriteSheet> getWriteSheetList(List<?> returnValue, ExcelResponse excelResponse) {
        List<WriteSheet> sheets = new ArrayList<>();
        if (excelResponse.sheets().length > 1) {
            List<List<?>> list = (List<List<?>>) returnValue;
            for (int i = 0; i < excelResponse.sheets().length; i++) {
                WriteSheet sheet = getWriteSheet(i, excelResponse.sheets()[i], list.get(i).getFirst().getClass());
                sheets.add(sheet);
            }
        } else {
            WriteSheet sheet = getWriteSheet(0, excelResponse.sheets()[0], returnValue.getFirst().getClass());
            sheets.add(sheet);
        }
        return sheets;
    }

    /**
     * 获取WriteSheet
     *
     * @param i         下标
     * @param sheet     sheet
     * @param headClass 头部类
     * @return WriteSheet
     */
    private WriteSheet getWriteSheet(int i, ExcelSheet sheet, Class<?> headClass) {
        final ExcelWriterSheetBuilder builder = EasyExcel.writerSheet(i, sheet.sheetName());
        // 设置参数
        // 设置是否需要头
        builder.needHead(sheet.needHead());
        builder.head(headClass);
        // 设置自动合并
        builder.automaticMergeHead(sheet.automaticMergeHead());
        // 设置包含字段
        if (ArrayUtil.isNotEmpty(sheet.include())) {
            builder.includeColumnFieldNames(CollUtil.newArrayList(sheet.include()));
        }
        // 设置排除字段
        if (ArrayUtil.isNotEmpty(sheet.exclude())) {
            builder.excludeColumnFieldNames(CollUtil.newArrayList(sheet.exclude()));
        }
        // 设置写处理器
        if (ArrayUtil.isNotEmpty(sheet.writeHandler())) {
            CollUtil.newArrayList(sheet.writeHandler())
                    .forEach(clazz -> builder.registerWriteHandler(BeanUtils.instantiateClass(clazz)));
        }
        // 设置转换器
        if (ArrayUtil.isNotEmpty(sheet.converter())) {
            CollUtil.newArrayList(sheet.converter())
                    .forEach(clazz -> builder.registerConverter(BeanUtils.instantiateClass(clazz)));
        }
        // 自定义处理
        if (ArrayUtil.isNotEmpty(sheet.customizer())) {
            CollUtil.newArrayList(sheet.customizer())
                    .forEach(clazz -> BeanUtils.instantiateClass(clazz).customize(builder));
        }
        return builder.build();
    }

    /**
     * 获取excelWriter
     *
     * @param response      response
     * @param excelResponse excelResponse
     * @return ExcelWriter
     * @throws IOException IOException
     */
    private ExcelWriter getExcelWriter(HttpServletResponse response, ExcelResponse excelResponse) throws IOException {
        final ExcelWriterBuilder builder = EasyExcel.write(response.getOutputStream());
        // 设置参数
        // 设置自动关闭流
        builder.autoCloseStream(excelResponse.autoCloseStream());
        // 设置excel类型
        builder.excelType(excelResponse.excelType());
        // 设置是否内存
        builder.inMemory(excelResponse.inMemory());
        // 设置模板
        if (StrUtil.isNotBlank(excelResponse.template())) {
            String templatePath = excelProperties.getTemplatePath();
            String template = templatePath + "/" + excelResponse.template();
            ClassPathResource classPathResource = new ClassPathResource(template);
            builder.withTemplate(classPathResource.getInputStream());
        }
        // 设置密码
        if (StrUtil.isNotBlank(excelResponse.password())) {
            builder.password(excelResponse.password());
        }
        // 设置包含字段
        if (ArrayUtil.isNotEmpty(excelResponse.include())) {
            builder.includeColumnFieldNames(CollUtil.newArrayList(excelResponse.include()));
        }
        // 设置排除字段
        if (ArrayUtil.isNotEmpty(excelResponse.exclude())) {
            builder.excludeColumnFieldNames(CollUtil.newArrayList(excelResponse.exclude()));
        }
        // 设置写处理器
        if (ArrayUtil.isNotEmpty(excelResponse.writeHandler())) {
            CollUtil.newArrayList(excelResponse.writeHandler())
                    .forEach(clazz -> builder.registerWriteHandler(BeanUtils.instantiateClass(clazz)));
        }
        // 设置默认转换器
        if (ArrayUtil.isNotEmpty(converters)) {
            converters.forEach(builder::registerConverter);
        }
        // 设置转换器
        if (ArrayUtil.isNotEmpty(excelResponse.converter())) {
            CollUtil.newArrayList(excelResponse.converter())
                    .forEach(clazz -> builder.registerConverter(BeanUtils.instantiateClass(clazz)));
        }
        // 自定义处理
        if (ArrayUtil.isNotEmpty(excelResponse.customizer())) {
            CollUtil.newArrayList(excelResponse.customizer())
                    .forEach(clazz -> BeanUtils.instantiateClass(clazz).customize(builder));
        }
        return builder.build();
    }
}
