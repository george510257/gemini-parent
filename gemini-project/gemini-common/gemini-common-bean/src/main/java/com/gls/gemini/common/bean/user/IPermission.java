package com.gls.gemini.common.bean.user;

import java.io.Serializable;

/**
 * 资源信息
 */
public interface IPermission extends Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

    /**
     * 获取资源名
     *
     * @return 资源名
     */
    String getName();

    /**
     * 获取资源编码
     *
     * @return 资源编码
     */
    String getCode();

    /**
     * 获取资源描述
     *
     * @return 资源描述
     */
    String getDescription();
}
