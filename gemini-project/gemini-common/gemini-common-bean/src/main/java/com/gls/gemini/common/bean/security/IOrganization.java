package com.gls.gemini.common.bean.security;

import java.io.Serializable;
import java.util.List;

public interface IOrganization<O extends IOrganization<O>> extends Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

    /**
     * 获取组织名
     *
     * @return 组织名
     */
    String getName();

    /**
     * 获取组织编码
     *
     * @return 组织编码
     */
    String getCode();

    /**
     * 获取组织描述
     *
     * @return 组织描述
     */
    String getDescription();

    /**
     * 获取父组织ID
     *
     * @return 父组织ID
     */
    Long getParentId();

    /**
     * 获取子组织列表
     *
     * @return 子组织列表
     */
    List<O> getChildren();

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();
}
