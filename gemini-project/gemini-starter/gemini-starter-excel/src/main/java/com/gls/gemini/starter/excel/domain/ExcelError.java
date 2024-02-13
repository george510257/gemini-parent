package com.gls.gemini.starter.excel.domain;

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
    private Long line;

    /**
     * 错误信息
     */
    private List<String> errors;
}
