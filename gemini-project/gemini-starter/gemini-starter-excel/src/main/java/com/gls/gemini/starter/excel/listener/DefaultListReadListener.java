package com.gls.gemini.starter.excel.listener;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.gls.gemini.starter.excel.annotation.ExcelLine;
import com.gls.gemini.starter.excel.domain.ExcelError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultListReadListener<T> implements ListReadListener<T> {

    private final List<T> list = new ArrayList<>();

    private final List<ExcelError> errors = new ArrayList<>();

    private Long line = 1L;

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 行号增加
        line++;

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
