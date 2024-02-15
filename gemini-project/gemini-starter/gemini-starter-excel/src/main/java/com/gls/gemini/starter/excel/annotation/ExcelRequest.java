package com.gls.gemini.starter.excel.annotation;

import com.gls.gemini.starter.excel.listener.DefaultListReadListener;
import com.gls.gemini.starter.excel.listener.ListReadListener;

import java.lang.annotation.*;

/**
 * excel请求 注解
 * 用于标识请求参数为excel文件
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelRequest {

    /**
     * 前端上传字段名称 file
     *
     * @return 默认file
     */
    String fileName() default "file";

    /**
     * 读取的标题行数
     *
     * @return 默认1
     */
    int headRowNumber() default 1;

    /**
     * 是否跳过空行
     *
     * @return 默认跳过
     */
    boolean ignoreEmptyRow() default false;

    /**
     * 读取的监听器类
     *
     * @return readListener
     */
    Class<? extends ListReadListener> readListener() default DefaultListReadListener.class;

}
