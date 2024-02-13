package com.gls.gemini.starter.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

import java.lang.annotation.*;

/**
 * ExcelResponse 注解
 * 用于标记方法，用于标记返回值为Excel的响应
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {

    /**
     * 文件名称
     *
     * @return 默认文件名称
     */
    String fileName() default "excel";

    /**
     * 文件类型 （xlsx xls）
     *
     * @return 默认xlsx
     */
    ExcelTypeEnum fileType() default ExcelTypeEnum.XLSX;

    /**
     * sheet
     *
     * @return 默认sheet1
     */
    ExcelSheet[] sheets() default @ExcelSheet(sheetName = "sheet1");

    /**
     * 包含字段
     *
     * @return String[]
     */
    String[] includeFields() default {};

    /**
     * 排除字段
     *
     * @return String[]
     */
    String[] excludeFields() default {};

    /**
     * 模板路径
     *
     * @return String
     */
    String template() default "";

    /**
     * 是否内存操作
     *
     * @return boolean
     */
    boolean inMemory() default false;

    /**
     * 文件密码
     *
     * @return String
     */
    String password() default "";

    /**
     * 拦截器
     *
     * @return WriteHandler[]
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 转换器
     *
     * @return Converter[]
     */
    Class<? extends Converter>[] converter() default {};

}
