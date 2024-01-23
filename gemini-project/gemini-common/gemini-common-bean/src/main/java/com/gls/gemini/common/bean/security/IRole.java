package com.gls.gemini.common.bean.security;

import com.gls.gemini.common.core.support.ITreeNode;
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
        return getCode();
    }

}
