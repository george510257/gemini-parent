package com.gls.gemini.common.bean.interfaces;

import java.io.Serializable;

/**
 * 枚举接口
 */
public interface IEnums extends Serializable {
    /**
     * 获取编码
     *
     * @return 返回编码
     */
    Integer getCode();

    /**
     * 获取名称
     *
     * @return 返回名称
     */
    String getMessage();

}
