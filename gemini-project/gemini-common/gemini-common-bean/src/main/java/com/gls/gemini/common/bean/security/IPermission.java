package com.gls.gemini.common.bean.security;

import java.io.Serializable;
import java.util.List;

/**
 * 资源信息
 */
public interface IPermission<P extends IPermission<P>> extends Serializable {
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

    /**
     * 获取父资源ID
     *
     * @return 父资源ID
     */
    Long getParentId();

    /**
     * 获取子资源列表
     *
     * @return 子资源列表
     */
    List<P> getChildren();

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();
}
