package com.gls.gemini.common.core.support;

import cn.hutool.core.lang.tree.Tree;
import com.gls.gemini.common.core.interfaces.IOrganization;
import com.gls.gemini.common.core.interfaces.IPermission;
import com.gls.gemini.common.core.interfaces.IRole;
import com.gls.gemini.common.core.interfaces.IUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/**
 * 登录模板
 */
@UtilityClass
public class LoginTemplate {

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public Optional<IUser> getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof IUser user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * 获取登录用户id
     *
     * @return 登录用户id
     */
    public Optional<Long> getLoginUserId() {
        return getLoginUser().map(IUser::getId);
    }

    /**
     * 获取登录租户id
     *
     * @return 登录租户id
     */
    public Optional<Long> getLoginTenantId() {
        return getLoginUser().map(IUser::getTenantId);
    }

    /**
     * 获取登录用户名
     *
     * @return 登录用户名
     */
    public Optional<String> getLoginUserName() {
        return getLoginUser().map(IUser::getUsername);
    }

    /**
     * 获取登录用户手机号
     *
     * @return 登录用户手机号
     */
    public Optional<String> getLoginUserMobile() {
        return getLoginUser().map(IUser::getMobile);
    }

    /**
     * 获取登录用户邮箱
     *
     * @return 登录用户邮箱
     */
    public Optional<String> getLoginUserEmail() {
        return getLoginUser().map(IUser::getEmail);
    }

    /**
     * 获取登录用户真实姓名
     *
     * @return 登录用户真实姓名
     */
    public Optional<String> getLoginUserRealName() {
        return getLoginUser().map(IUser::getRealName);
    }

    /**
     * 获取登录用户昵称
     *
     * @return 登录用户昵称
     */
    public Optional<String> getLoginUserNickname() {
        return getLoginUser().map(IUser::getNickname);
    }

    /**
     * 获取登录用户头像
     *
     * @return 登录用户头像
     */
    public Optional<String> getLoginUserAvatar() {
        return getLoginUser().map(IUser::getAvatar);
    }

    /**
     * 获取登录用户语言
     *
     * @return 登录用户语言
     */
    public Optional<String> getLoginUserLanguage() {
        return getLoginUser().map(IUser::getLanguage);
    }

    /**
     * 获取登录用户区域
     *
     * @return 登录用户区域
     */
    public Optional<Locale> getLoginUserLocale() {
        return getLoginUser().map(IUser::getLocale);
    }

    /**
     * 获取登录用户时区
     *
     * @return 登录用户时区
     */
    public Optional<TimeZone> getLoginUserTimeZone() {
        return getLoginUser().map(IUser::getTimeZone);
    }

    /**
     * 获取登录用户角色
     *
     * @return 登录用户角色
     */
    public Optional<IRole> getLoginUserRole() {
        return getLoginUser().map(IUser::getRole);
    }

    /**
     * 获取登录用户角色id
     *
     * @return 登录用户角色id
     */
    public Optional<Long> getLoginUserRoleId() {
        return getLoginUserRole().map(IRole::getId);
    }

    /**
     * 获取登录用户角色名称
     *
     * @return 登录用户角色名称
     */
    public Optional<String> getLoginUserRoleName() {
        return getLoginUserRole().map(IRole::getName);
    }

    /**
     * 获取登录用户角色编码
     *
     * @return 登录用户角色编码
     */
    public Optional<String> getLoginUserRoleCode() {
        return getLoginUserRole().map(IRole::getCode);
    }

    /**
     * 获取登录用户角色列表
     *
     * @return 登录用户角色列表
     */
    public Optional<List<IRole>> getLoginUserRoleList() {
        return getLoginUser().map(IUser::getRoles);
    }

    /**
     * 获取登录用户角色树
     *
     * @return 登录用户角色树
     */
    public Optional<List<Tree<Long>>> getLoginUserRoleTree() {
        return getLoginUser().map(IUser::getRoleTree);
    }

    /**
     * 获取登录用户组织
     *
     * @return 登录用户组织
     */
    public Optional<IOrganization> getLoginUserOrganization() {
        return getLoginUser().map(IUser::getOrganization);
    }

    /**
     * 获取登录用户组织id
     *
     * @return 登录用户组织id
     */
    public Optional<Long> getLoginUserOrganizationId() {
        return getLoginUserOrganization().map(IOrganization::getId);
    }

    /**
     * 获取登录用户组织名称
     *
     * @return 登录用户组织名称
     */
    public Optional<String> getLoginUserOrganizationName() {
        return getLoginUserOrganization().map(IOrganization::getName);
    }

    /**
     * 获取登录用户组织编码
     *
     * @return 登录用户组织编码
     */
    public Optional<String> getLoginUserOrganizationCode() {
        return getLoginUserOrganization().map(IOrganization::getCode);
    }

    /**
     * 获取登录用户组织列表
     *
     * @return 登录用户组织列表
     */
    public Optional<List<IOrganization>> getLoginUserOrganizationList() {
        return getLoginUser().map(IUser::getOrganizations);
    }

    /**
     * 获取登录用户组织树
     *
     * @return 登录用户组织树
     */
    public Optional<List<Tree<Long>>> getLoginUserOrganizationTree() {
        return getLoginUser().map(IUser::getOrganizationTree);
    }

    /**
     * 获取登录用户权限列表
     *
     * @return 登录用户权限列表
     */
    public Optional<List<IPermission>> getLoginUserPermissionList() {
        return getLoginUser().map(IUser::getPermissions);
    }

    /**
     * 获取登录用户权限树
     *
     * @return 登录用户权限树
     */
    public Optional<List<Tree<Long>>> getLoginUserPermissionTree() {
        return getLoginUser().map(IUser::getPermissionTree);
    }

    /**
     * 判断是否存在权限
     *
     * @param command 权限
     * @return 是否存在权限
     */
    public boolean hasPermission(String command) {
        return getLoginUserPermissionList()
                .map(permissions -> permissions.stream()
                        .anyMatch(permission -> permission.getCommand().contains(command)))
                .orElse(false);
    }
}
