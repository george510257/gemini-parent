package com.gls.gemini.starter.excel.listener;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.gls.gemini.starter.excel.annotation.ExcelLine;
import com.gls.gemini.starter.excel.domain.ExcelError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(data);

        // 校验不通过
        if (CollUtil.isNotEmpty(violations)) {
            List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            log.error("第{}行数据校验不通过: {}", line, messages);
            errors.add(ExcelError.builder().line(line).errors(messages).build());
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
