package com.gls.gemini.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询VO
 */
@Data
public class PageQuery<T> implements Serializable {

    /**
     * 页码
     */
    private Integer page = 1;
    /**
     * 每页条数
     */
    private Integer size = 10;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 排序方式
     */
    private String order;
    /**
     * 查询参数
     */
    private T params;

}
