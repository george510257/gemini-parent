package com.gls.gemini.common.bean.interfaces;

import org.springframework.security.core.GrantedAuthority;

/**
 * 角色信息
 */
public interface IRole extends ITreeNode, GrantedAuthority {
    /**
     * 获取角色编码
     *
     * @return 角色编码
     */
    @Override
    default String getAuthority() {
        return this.getCode();
    }

}
