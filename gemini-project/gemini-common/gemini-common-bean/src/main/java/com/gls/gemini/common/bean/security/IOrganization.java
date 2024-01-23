package com.gls.gemini.common.bean.security;

import com.gls.gemini.common.core.base.ITree;

public interface IOrganization<O extends IOrganization<O>> extends ITree<O> {

    /**
     * 获取组织描述
     *
     * @return 组织描述
     */
    String getDescription();

}
