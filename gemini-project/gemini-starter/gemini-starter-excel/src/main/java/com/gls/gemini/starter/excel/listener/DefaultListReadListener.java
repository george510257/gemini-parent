package com.gls.gemini.starter.excel.listener;

import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.gls.gemini.starter.excel.annotation.ExcelLine;
import com.gls.gemini.starter.excel.annotation.ExcelMultiColumn;
import com.gls.gemini.starter.excel.support.ExcelError;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DefaultListReadListener<T> implements ListReadListener<T> {

    private final List<T> list = new ArrayList<>();
    private final List<ExcelError> errors = new ArrayList<>();
    private final Map<Integer, String> headMap = new HashMap<>();
    private Long line = 0L;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析异常", exception);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> head, AnalysisContext context) {
        headMap.putAll(ConverterUtils.convertToStringMap(head, context));
        log.info("解析到一条头数据: {}", headMap);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("解析到一条额外数据: {}", extra);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        line++;
        Map<Integer, Cell> cellMap = context.readRowHolder().getCellMap();
        log.info("解析第{}行数据: {}", line, JSONUtil.toJsonStr(cellMap));
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取有ExcelLine注解的字段 并且类型为Long 设置行号
            if (field.isAnnotationPresent(ExcelLine.class) && field.getType().isAssignableFrom(Long.class)) {
                try {
                    field.setAccessible(true);
                    field.set(data, line);
                } catch (IllegalAccessException e) {
                    log.error("设置行号失败", e);
                }
            }
            if (field.isAnnotationPresent(ExcelMultiColumn.class) && field.getType().isAssignableFrom(Map.class)) {
                Map<String, Object> map = new HashMap<>();
                ExcelMultiColumn excelMultiColumn = field.getAnnotation(ExcelMultiColumn.class);
                int start = excelMultiColumn.start();
                int end = excelMultiColumn.end();
                if (end > headMap.size()) {
                    end = headMap.size();
                }
                for (int i = start; i <= end; i++) {
                    String key = headMap.get(i);
                    Object value = getValue(cellMap.get(i));
                    map.put(key, value);
                }
                try {
                    field.setAccessible(true);
                    field.set(data, map);
                } catch (IllegalAccessException e) {
                    log.error("设置多列失败", e);
                }
            }
        }
        log.info("解析第{}行数据: {}", line, data);
        // 校验
        BeanValidationResult result = ValidationUtil.warpValidate(data);
        if (!result.isSuccess()) {
            log.error("第{}行数据校验不通过: {}", line, result.getErrorMessages());
            errors.add(ExcelError.builder().line(line).errorMessages(result.getErrorMessages()).build());
            return;
        }
        list.add(data);
    }

    private Object getValue(Cell cell) {
        if (cell instanceof ReadCellData<?> readCellData) {
            return switch (readCellData.getType()) {
                case STRING -> readCellData.getStringValue();
                case BOOLEAN -> readCellData.getBooleanValue();
                case NUMBER -> readCellData.getNumberValue();
                default -> readCellData.getData();
            };
        }
        return null;
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
