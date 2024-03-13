package com.gls.gemini.common.core.interfaces;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 角色信息
 */
public interface IRole<P extends IPermission> extends ITreeNode, GrantedAuthority {
    /**
     * 获取角色编码
     *
     * @return 角色编码
     */
    @Override
    default String getAuthority() {
        return this.getCode();
    }

    /**
     * 获取权限列表
     *
     * @return 权限列表
     */
    List<P> getPermissions();

}
