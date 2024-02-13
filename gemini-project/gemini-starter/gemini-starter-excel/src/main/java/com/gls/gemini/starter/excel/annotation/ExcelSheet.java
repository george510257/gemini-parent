package com.gls.gemini.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelSheet 注解
 * 用于标记实体类中的方法，用于标记Excel中的sheet
 * 用于标记方法，用于标记返回值为Excel的响应
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    /**
     * sheet名称
     *
     * @return 默认sheet1
     */
    String sheetName() default "sheet1";

    /**
     * 包含字段
     */
    String[] includeFields() default {};

    /**
     * 排除字段
     */
    String[] excludeFields() default {};

}
