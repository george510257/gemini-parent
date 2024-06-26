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

/**
 * 默认List读取监听器
 *
 * @param <T> 读取对象
 */
@Slf4j
public class DefaultListReadListener<T> implements ListReadListener<T> {
    /**
     * 对象列表
     */
    private final List<T> list = new ArrayList<>();
    /**
     * 错误信息
     */
    private final List<ExcelError> errors = new ArrayList<>();
    /**
     * 头信息
     */
    private final Map<Integer, String> headMap = new HashMap<>();

    /**
     * 解析异常
     *
     * @param exception 异常
     * @param context   上下文
     * @throws Exception 异常
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析异常", exception);
    }

    /**
     * 解析头数据
     *
     * @param head    头数据
     * @param context 上下文
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> head, AnalysisContext context) {
        headMap.putAll(ConverterUtils.convertToStringMap(head, context));
        log.info("解析到一条头数据: {}", headMap);
    }

    /**
     * 解析额外数据
     *
     * @param extra   额外数据
     * @param context 上下文
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("解析到一条额外数据: {}", extra);
    }

    /**
     * 解析数据
     *
     * @param data    数据
     * @param context 上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        Integer line = context.readRowHolder().getRowIndex();
        Map<Integer, Cell> cellMap = context.readRowHolder().getCellMap();
        log.info("解析第{}行数据: {}", line, JSONUtil.toJsonStr(cellMap));
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取有ExcelLine注解的字段 并且类型为Integer 设置行号
            if (field.isAnnotationPresent(ExcelLine.class) && field.getType().isAssignableFrom(Integer.class)) {
                try {
                    field.setAccessible(true);
                    field.set(data, line);
                } catch (IllegalAccessException e) {
                    log.error("设置行号失败", e);
                }
            }
            // 获取有ExcelMultiColumn注解的字段 并且类型为Map 设置多列
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

    /**
     * 获取值
     *
     * @param cell 单元格
     * @return 值
     */
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

    /**
     * 解析完成
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析完成");
    }

    /**
     * 获取对象列表
     *
     * @return 对象列表
     */
    @Override
    public List<T> getList() {
        return list;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    @Override
    public List<ExcelError> getErrors() {
        return errors;
    }
}
