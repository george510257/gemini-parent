package com.gls.gemini.common.bean.security;

import com.gls.gemini.common.core.base.ITree;

/**
 * 资源信息
 */
public interface IPermission<P extends IPermission<P>> extends ITree<P> {

    /**
     * 获取资源描述
     *
     * @return 资源描述
     */
    String getDescription();
}
