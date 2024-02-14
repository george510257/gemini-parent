package com.gls.gemini.common.core.domain;

import com.gls.gemini.common.core.interfaces.IEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 返回结果
 *
 * @param <T> 返回数据类型
 * @author george
 */
@Data
@Schema(title = "返回结果", description = "返回结果")
public class Result<T> implements IEnums {

    @Schema(title = "返回码", description = "返回码")
    private Integer code;
    @Schema(title = "返回信息", description = "返回信息")
    private String message;
    @Schema(title = "跟踪ID", description = "跟踪ID")
    private String traceId;
    @Schema(title = "返回数据", description = "返回数据")
    private T data;

    public Result() {
    }

    public Result(IEnums enums) {
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    public Result(IEnums enums, T data) {
        this.code = enums.getCode();
        this.message = enums.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}