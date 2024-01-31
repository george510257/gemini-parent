package com.gls.gemini.common.core.interfaces;

/**
 * 资源信息
 */
public interface IPermission extends ITreeNode {

    /**
     * 获取资源指令 例如：/user/add
     *
     * @return 资源指令
     */
    String getCommand();

}
