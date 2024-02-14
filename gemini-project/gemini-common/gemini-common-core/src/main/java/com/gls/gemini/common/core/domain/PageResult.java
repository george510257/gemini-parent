package com.gls.gemini.common.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "分页结果", description = "分页结果")
public class PageResult<T> implements Serializable {

    @Schema(title = "页码", description = "页码")
    private Integer page;
    @Schema(title = "每页条数", description = "每页条数")
    private Integer size;
    @Schema(title = "总条数", description = "总条数")
    private Long total;
    @Schema(title = "总页数", description = "总页数")
    private Integer totalPage;
    @Schema(title = "数据", description = "数据")
    private List<T> records;

}
