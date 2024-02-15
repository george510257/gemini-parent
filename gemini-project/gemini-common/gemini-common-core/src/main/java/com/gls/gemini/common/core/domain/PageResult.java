package com.gls.gemini.common.core.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer size;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 数据
     */
    private List<T> records;

}
