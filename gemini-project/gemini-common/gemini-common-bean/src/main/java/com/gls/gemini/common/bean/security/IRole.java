package com.gls.gemini.common.bean.security;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息
 */
public interface IRole<R extends IRole<R>> extends Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

    /**
     * 获取角色名
     *
     * @return 角色名
     */
    String getName();

    /**
     * 获取角色编码
     *
     * @return 角色编码
     */
    String getCode();

    /**
     * 获取角色描述
     *
     * @return 角色描述
     */
    String getDescription();

    /**
     * 获取父角色ID
     *
     * @return 父角色ID
     */
    Long getParentId();

    /**
     * 获取子角色列表
     *
     * @return 子角色列表
     */
    List<R> getChildren();

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();

}
