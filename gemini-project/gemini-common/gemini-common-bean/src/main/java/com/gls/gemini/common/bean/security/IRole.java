package com.gls.gemini.common.bean.security;

import com.gls.gemini.common.core.tree.ITreeNode;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色信息
 */
public interface IRole extends ITreeNode, GrantedAuthority {

}
