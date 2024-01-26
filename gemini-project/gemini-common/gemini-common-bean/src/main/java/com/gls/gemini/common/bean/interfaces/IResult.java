package com.gls.gemini.common.bean.interfaces;

/**
 * 返回结果
 *
 * @param <Data> 数据类型
 */
public interface IResult<Data> extends IEnums {
    /**
     * 获取跟踪ID
     *
     * @return 返回跟踪ID
     */
    String getTraceId();

    /**
     * 获取数据
     *
     * @return 返回数据
     */
    Data getData();

}
