package com.gls.gemini.starter.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.gls.gemini.starter.excel.support.ExcelError;

import java.util.List;

public interface ListReadListener<T> extends ReadListener<T> {

    List<T> getList();

    List<ExcelError> getErrors();
}
