package com.gls.gemini.starter.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.gls.gemini.starter.excel.customizer.Customizer;

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
     * 是否自动关闭流
     *
     * @return 默认自动关闭
     */
    boolean autoCloseStream() default true;

    /**
     * 文件类型
     *
     * @return 默认xlsx
     */
    ExcelTypeEnum excelType() default ExcelTypeEnum.XLSX;

    /**
     * 是否内存操作
     *
     * @return 默认内存操作
     */
    boolean inMemory() default true;

    /**
     * 文件密码
     *
     * @return 默认无密码
     */
    String password() default "";

    /**
     * 包含字段
     *
     * @return 默认为空
     */
    String[] include() default {};

    /**
     * 排除字段
     *
     * @return 默认为空
     */
    String[] exclude() default {};

    /**
     * 写处理器
     *
     * @return 默认为空
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 转换器
     *
     * @return 默认为空
     */
    Class<? extends Converter<?>>[] converter() default {};

    /**
     * 模板
     *
     * @return 默认为空
     */
    String template() default "";

    /**
     * 自定义处理
     *
     * @return 默认为空
     */
    Class<? extends Customizer<ExcelWriterBuilder>>[] customizer() default {};

    /**
     * sheet
     *
     * @return ExcelSheet[]
     */
    ExcelSheet[] sheets() default @ExcelSheet;

    /**
     * 自动填充
     *
     * @return boolean
     */
    boolean autoFill() default false;
}
