package com.gls.gemini.common.bean.result;

import com.gls.gemini.common.core.support.IEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @param <T> 返回数据类型
 * @author george
 */
@Data
@Accessors(chain = true)
@Schema(title = "返回结果", description = "返回结果")
public class Result<T> implements IEnums, Serializable {

    @Schema(title = "返回码", description = "返回码")
    private Integer code;
    @Schema(title = "返回信息", description = "返回信息")
    private String message;
    @Schema(title = "跟踪ID", description = "跟踪ID")
    private String traceId;
    @Schema(title = "返回数据", description = "返回数据")
    private T data;
}