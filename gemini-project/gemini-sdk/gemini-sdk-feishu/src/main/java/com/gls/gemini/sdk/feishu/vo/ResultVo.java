package com.gls.gemini.sdk.feishu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @param <T> 返回数据类型
 */
@Data
public class ResultVo<T> implements Serializable {
    /**
     * code : 错误码，非 0 取值表示失败
     */
    @JsonProperty("code")
    private Integer code;
    /**
     * msg : 错误信息
     */
    @JsonProperty("msg")
    private String msg;
    /**
     * data : 返回数据
     */
    @JsonProperty("data")
    private T data;

}
