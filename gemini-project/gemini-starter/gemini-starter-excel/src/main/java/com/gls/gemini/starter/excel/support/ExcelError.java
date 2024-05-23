package com.gls.gemini.starter.excel.support;

import cn.hutool.extra.validation.BeanValidationResult;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * excel 错误信息
 */
@Data
@Builder
public class ExcelError implements Serializable {
    /**
     * 行号
     */
    private Integer line;
    /**
     * 错误信息
     */
    private List<BeanValidationResult.ErrorMessage> errorMessages;
}
