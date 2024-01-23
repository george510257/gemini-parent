package com.gls.gemini.common.bean.security;

import cn.hutool.core.lang.tree.Tree;
import com.gls.gemini.common.core.tree.TreeUtil;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 用户信息
 */
public interface IUser<R extends IRole, P extends IPermission, O extends IOrganization>
        extends UserDetails, Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

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
    String getNickname();

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
    List<P> getPermissions();

    /**
     * 获取组织机构列表
     *
     * @return 组织机构列表
     */
    List<O> getOrganizations();

    /**
     * 获取角色树
     *
     * @return 角色树
     */
    default List<Tree<Long>> getRoleTree() {
        return TreeUtil.buildTree(this.getRoles());
    }

    /**
     * 获取权限树
     *
     * @return 权限树
     */
    default List<Tree<Long>> getPermissionTree() {
        return TreeUtil.buildTree(this.getPermissions());
    }

    /**
     * 获取组织机构树
     *
     * @return 组织机构树
     */
    default List<Tree<Long>> getOrganizationTree() {
        return TreeUtil.buildTree(this.getOrganizations());
    }
}
