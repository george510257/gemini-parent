package com.gls.gemini.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelMultiColumn 注解 用于标记实体类中的字段，用于标记Excel中的多列
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMultiColumn {
    /**
     * 开始列
     *
     * @return
     */
    int start() default 0;

    /**
     * 结束列
     *
     * @return
     */
    int end() default Integer.MAX_VALUE;
}
