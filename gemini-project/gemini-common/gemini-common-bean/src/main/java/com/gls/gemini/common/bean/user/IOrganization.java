package com.gls.gemini.common.bean.user;

import java.io.Serializable;

public interface IOrganization extends Serializable {
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
}
