package com.gls.gemini.common.bean.user;

import java.io.Serializable;

/**
 * 角色信息
 */
public interface IRole extends Serializable {
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
}
