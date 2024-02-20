package com.gls.gemini.common.core.support;

import cn.hutool.core.lang.tree.Tree;
import com.gls.gemini.common.core.interfaces.IOrganization;
import com.gls.gemini.common.core.interfaces.IPermission;
import com.gls.gemini.common.core.interfaces.IRole;
import com.gls.gemini.common.core.interfaces.IUser;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/**
 * 登录模板
 */
public interface LoginTemplate<U extends IUser<R, P, O>, R extends IRole, P extends IPermission, O extends IOrganization> {

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    Optional<U> getLoginUser();

    /**
     * 获取登录用户id
     *
     * @return 登录用户id
     */
    default Optional<Long> getLoginUserId() {
        return getLoginUser().map(IUser::getId);
    }

    /**
     * 获取登录租户id
     *
     * @return 登录租户id
     */
    default Optional<Long> getLoginTenantId() {
        return getLoginUser().map(IUser::getTenantId);
    }

    /**
     * 获取登录用户名
     *
     * @return 登录用户名
     */
    default Optional<String> getLoginUserName() {
        return getLoginUser().map(IUser::getUsername);
    }

    /**
     * 获取登录用户手机号
     *
     * @return 登录用户手机号
     */
    default Optional<String> getLoginUserMobile() {
        return getLoginUser().map(IUser::getMobile);
    }

    /**
     * 获取登录用户邮箱
     *
     * @return 登录用户邮箱
     */
    default Optional<String> getLoginUserEmail() {
        return getLoginUser().map(IUser::getEmail);
    }

    /**
     * 获取登录用户真实姓名
     *
     * @return 登录用户真实姓名
     */
    default Optional<String> getLoginUserRealName() {
        return getLoginUser().map(IUser::getRealName);
    }

    /**
     * 获取登录用户昵称
     *
     * @return 登录用户昵称
     */
    default Optional<String> getLoginUserNickname() {
        return getLoginUser().map(IUser::getNickname);
    }

    /**
     * 获取登录用户头像
     *
     * @return 登录用户头像
     */
    default Optional<String> getLoginUserAvatar() {
        return getLoginUser().map(IUser::getAvatar);
    }

    /**
     * 获取登录用户语言
     *
     * @return 登录用户语言
     */
    default Optional<String> getLoginUserLanguage() {
        return getLoginUser().map(IUser::getLanguage);
    }

    /**
     * 获取登录用户区域
     *
     * @return 登录用户区域
     */
    default Optional<Locale> getLoginUserLocale() {
        return getLoginUser().map(IUser::getLocale);
    }

    /**
     * 获取登录用户时区
     *
     * @return 登录用户时区
     */
    default Optional<TimeZone> getLoginUserTimeZone() {
        return getLoginUser().map(IUser::getTimeZone);
    }

    /**
     * 获取登录用户角色
     *
     * @return 登录用户角色
     */
    default Optional<R> getLoginUserRole() {
        return getLoginUser().map(IUser::getRole);
    }

    /**
     * 获取登录用户角色id
     *
     * @return 登录用户角色id
     */
    default Optional<Long> getLoginUserRoleId() {
        return getLoginUserRole().map(IRole::getId);
    }

    /**
     * 获取登录用户角色名称
     *
     * @return 登录用户角色名称
     */
    default Optional<String> getLoginUserRoleName() {
        return getLoginUserRole().map(IRole::getName);
    }

    /**
     * 获取登录用户角色编码
     *
     * @return 登录用户角色编码
     */
    default Optional<String> getLoginUserRoleCode() {
        return getLoginUserRole().map(IRole::getCode);
    }

    /**
     * 获取登录用户角色列表
     *
     * @return 登录用户角色列表
     */
    default Optional<List<R>> getLoginUserRoleList() {
        return getLoginUser().map(IUser::getRoles);
    }

    /**
     * 获取登录用户角色树
     *
     * @return 登录用户角色树
     */
    default Optional<List<Tree<Long>>> getLoginUserRoleTree() {
        return getLoginUser().map(IUser::getRoleTree);
    }

    /**
     * 获取登录用户组织
     *
     * @return 登录用户组织
     */
    default Optional<O> getLoginUserOrganization() {
        return getLoginUser().map(IUser::getOrganization);
    }

    /**
     * 获取登录用户组织id
     *
     * @return 登录用户组织id
     */
    default Optional<Long> getLoginUserOrganizationId() {
        return getLoginUserOrganization().map(IOrganization::getId);
    }

    /**
     * 获取登录用户组织名称
     *
     * @return 登录用户组织名称
     */
    default Optional<String> getLoginUserOrganizationName() {
        return getLoginUserOrganization().map(IOrganization::getName);
    }

    /**
     * 获取登录用户组织编码
     *
     * @return 登录用户组织编码
     */
    default Optional<String> getLoginUserOrganizationCode() {
        return getLoginUserOrganization().map(IOrganization::getCode);
    }

    /**
     * 获取登录用户组织列表
     *
     * @return 登录用户组织列表
     */
    default Optional<List<O>> getLoginUserOrganizationList() {
        return getLoginUser().map(IUser::getOrganizations);
    }

    /**
     * 获取登录用户组织树
     *
     * @return 登录用户组织树
     */
    default Optional<List<Tree<Long>>> getLoginUserOrganizationTree() {
        return getLoginUser().map(IUser::getOrganizationTree);
    }

    /**
     * 获取登录用户权限列表
     *
     * @return 登录用户权限列表
     */
    default Optional<List<P>> getLoginUserPermissionList() {
        return getLoginUser().map(IUser::getPermissions);
    }

    /**
     * 获取登录用户权限树
     *
     * @return 登录用户权限树
     */
    default Optional<List<Tree<Long>>> getLoginUserPermissionTree() {
        return getLoginUser().map(IUser::getPermissionTree);
    }

    /**
     * 判断是否存在权限
     *
     * @param command 权限
     * @return 是否存在权限
     */
    default boolean hasPermission(String command) {
        return getLoginUserPermissionList()
                .map(permissions -> permissions.stream()
                        .anyMatch(permission -> permission.getCommand().contains(command)))
                .orElse(false);
    }
}
