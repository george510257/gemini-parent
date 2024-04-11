package com.gls.gemini.starter.excel.listener;

import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.gls.gemini.starter.excel.annotation.ExcelLine;
import com.gls.gemini.starter.excel.support.ExcelError;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class DefaultListReadListener<T> implements ListReadListener<T> {

    private final List<T> list = new ArrayList<>();

    private final List<ExcelError> errors = new ArrayList<>();

    private Long line = 1L;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析异常", exception);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Map<Integer, String> head = ConverterUtils.convertToStringMap(headMap, context);
        log.info("解析到一条头数据: {}", head);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("解析到一条额外数据: {}", extra);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 行号增加
        line++;
        log.info("解析第{}行数据: {}", line, data);

        // 校验
        BeanValidationResult result = ValidationUtil.warpValidate(data);

        if (!result.isSuccess()) {
            log.error("第{}行数据校验不通过: {}", line, result.getErrorMessages());
            errors.add(ExcelError.builder().line(line).errorMessages(result.getErrorMessages()).build());
            return;
        }
        // 校验通过
        // 获取有ExcelLine注解的字段 并且类型为Long 设置行号
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelLine.class) && field.getType() == Long.class) {
                try {
                    field.setAccessible(true);
                    field.set(data, line);
                } catch (IllegalAccessException e) {
                    log.error("设置行号失败", e);
                }
            }
        }
        list.add(data);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析完成");
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public List<ExcelError> getErrors() {
        return errors;
    }
}
