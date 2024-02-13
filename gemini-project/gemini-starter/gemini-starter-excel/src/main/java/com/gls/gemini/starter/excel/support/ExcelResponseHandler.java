package com.gls.gemini.starter.excel.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.gls.gemini.starter.excel.annotation.ExcelResponse;
import com.gls.gemini.starter.excel.annotation.ExcelSheet;
import com.gls.gemini.starter.excel.constants.ExcelProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelResponseHandler implements HandlerMethodReturnValueHandler {
    @Resource
    private ExcelProperties excelProperties;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelResponse.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 获取返回值类型
        Class<?> parameterType = returnType.getParameterType();
        // 如果不是List类型, 抛出异常
        if (!parameterType.isAssignableFrom(List.class)) {
            throw new IllegalArgumentException("Excel download response handler error, @ExcelResponse return value is not List " + parameterType);
        }

        // 获取请求参数
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
        assert excelResponse != null;

        // 获取响应参数
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        assert response != null;
        // 设置响应参数
        mavContainer.setRequestHandled(true);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + excelResponse.fileName() + ".xlsx");

        // 导出excel
        ExcelWriter excelWriter = getExcelWriter(response, excelResponse);
        List<WriteSheet> sheets = getWriteSheetList(excelResponse);
        // 写入数据
        if (sheets.size() > 1) {
            manySheetWrite((List<List<?>>) returnValue, excelWriter, sheets, excelResponse.autoFill());
        } else {
            singleSheetWrite((List<?>) returnValue, excelWriter, sheets.getFirst(), excelResponse.autoFill());
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
     * @param excelResponse excelResponse
     * @return WriteSheet列表
     */
    private List<WriteSheet> getWriteSheetList(ExcelResponse excelResponse) {
        List<WriteSheet> sheets = new ArrayList<>();
        for (int i = 0; i < excelResponse.sheets().length; i++) {
            WriteSheet sheet = getWriteSheet(i, excelResponse.sheets()[i]);
            sheets.add(sheet);
        }
        return sheets;
    }

    /**
     * 获取WriteSheet
     *
     * @param i     下标
     * @param sheet sheet
     * @return WriteSheet
     */
    private WriteSheet getWriteSheet(int i, ExcelSheet sheet) {
        final ExcelWriterSheetBuilder builder = EasyExcel.writerSheet(i, sheet.sheetName());
        // 设置参数
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
                    .forEach(clazz -> builder.registerWriteHandler(SpringUtil.getBean(clazz)));
        }
        // 设置转换器
        if (ArrayUtil.isNotEmpty(sheet.converter())) {
            CollUtil.newArrayList(sheet.converter())
                    .forEach(clazz -> builder.registerConverter(SpringUtil.getBean(clazz)));
        }
        // 自定义处理
        if (ArrayUtil.isNotEmpty(sheet.customizer())) {
            CollUtil.newArrayList(sheet.customizer())
                    .forEach(clazz -> SpringUtil.getBean(clazz).customize(builder));
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
                    .forEach(clazz -> builder.registerWriteHandler(SpringUtil.getBean(clazz)));
        }
        // 设置转换器
        if (ArrayUtil.isNotEmpty(excelResponse.converter())) {
            CollUtil.newArrayList(excelResponse.converter())
                    .forEach(clazz -> builder.registerConverter(SpringUtil.getBean(clazz)));
        }
        // 自定义处理
        if (ArrayUtil.isNotEmpty(excelResponse.customizer())) {
            CollUtil.newArrayList(excelResponse.customizer())
                    .forEach(clazz -> SpringUtil.getBean(clazz).customize(builder));
        }
        return builder.build();
    }
}
