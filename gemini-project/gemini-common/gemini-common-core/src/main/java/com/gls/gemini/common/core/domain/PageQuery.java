package com.gls.gemini.common.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询VO
 */
@Data
@Schema(title = "分页查询VO", description = "分页查询VO")
public class PageQuery<T> implements Serializable {

    @Schema(title = "页码", description = "页码")
    private Integer page = 1;
    @Schema(title = "每页条数", description = "每页条数")
    private Integer size = 10;
    @Schema(title = "排序字段", description = "排序字段")
    private String sort;
    @Schema(title = "排序方式", description = "排序方式")
    private String order;
    @Schema(title = "查询参数", description = "查询参数")
    private T params;

}
