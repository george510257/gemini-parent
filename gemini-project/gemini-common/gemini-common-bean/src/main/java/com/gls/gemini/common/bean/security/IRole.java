package com.gls.gemini.common.bean.security;

import com.gls.gemini.common.core.base.ITree;

/**
 * 角色信息
 */
public interface IRole<R extends IRole<R>> extends ITree<R> {

    /**
     * 获取角色描述
     *
     * @return 角色描述
     */
    String getDescription();

}
