package com.gls.gemini.common.bean.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @param <T> 返回数据类型
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 跟踪ID
     */
    private String traceId;
    /**
     * 返回数据
     */
    private T data;
}