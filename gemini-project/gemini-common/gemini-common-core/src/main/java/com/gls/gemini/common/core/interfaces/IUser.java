package com.gls.gemini.common.core.interfaces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.gls.gemini.common.core.support.ITreeNodeParser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * 用户信息
 */
public interface IUser<R extends IRole<P>, P extends IPermission, O extends IOrganization> extends UserDetails, IDomain {

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    String getMobile();

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    String getEmail();

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    String getRealName();

    /**
     * 获取昵称
     *
     * @return 昵称
     */
    String getNickName();

    /**
     * 获取用户头像
     *
     * @return 头像
     */
    String getAvatar();

    /**
     * 获取用户语言
     *
     * @return 语言
     */
    String getLanguage();

    /**
     * 获取用户区域
     *
     * @return 区域
     */
    Locale getLocale();

    /**
     * 获取用户时区
     *
     * @return 时区
     */
    TimeZone getTimeZone();

    /**
     * 获取当前角色
     *
     * @return 当前角色
     */
    R getRole();

    /**
     * 获取当前组织
     *
     * @return 当前组织
     */
    O getOrganization();

    /**
     * 获取用户角色列表
     *
     * @return 角色列表
     */
    List<R> getRoles();

    /**
     * 获取用户权限列表
     *
     * @return 权限列表
     */
    default List<P> getPermissions() {
        if (CollUtil.isEmpty(this.getRoles())) {
            return null;
        }
        return this.getRoles().stream()
                .map(IRole::getPermissions)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取组织机构列表
     *
     * @return 组织机构列表
     */
    List<O> getOrganizations();

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    /**
     * 账户是否过期
     *
     * @return 是否过期 true:未过期 false:已过期
     */
    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     *
     * @return 是否锁定 true:未锁定 false:已锁定
     */
    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     *
     * @return 是否过期 true:未过期 false:已过期
     */
    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     *
     * @return 是否启用 true:启用 false:禁用
     */
    @Override
    default boolean isEnabled() {
        return true;
    }

    /**
     * 获取角色树
     *
     * @return 角色树
     */
    default List<Tree<Long>> getRoleTree() {
        if (CollUtil.isEmpty(this.getRoles())) {
            return null;
        }
        return TreeUtil.build(this.getRoles(), 0L, new ITreeNodeParser<>());
    }

    /**
     * 获取权限树
     *
     * @return 权限树
     */
    default List<Tree<Long>> getPermissionTree() {
        if (CollUtil.isEmpty(this.getPermissions())) {
            return null;
        }
        return TreeUtil.build(this.getPermissions(), 0L, new ITreeNodeParser<>());
    }

    /**
     * 获取组织机构树
     *
     * @return 组织机构树
     */
    default List<Tree<Long>> getOrganizationTree() {
        if (CollUtil.isEmpty(this.getOrganizations())) {
            return null;
        }
        return TreeUtil.build(this.getOrganizations(), 0L, new ITreeNodeParser<>());
    }

}
