package com.gls.gemini.starter.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.gls.gemini.starter.excel.support.ExcelError;

import java.util.List;

/**
 * List读取监听器
 *
 * @param <T> 读取对象
 */
public interface ListReadListener<T> extends ReadListener<T> {
    /**
     * 获取对象列表
     *
     * @return 集合
     */
    List<T> getList();

    /**
     * 获取异常校验结果
     *
     * @return 集合
     */
    List<ExcelError> getErrors();
}
