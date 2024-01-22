package com.gls.gemini.common.bean.user;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 用户信息
 */
public interface IUser extends Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

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
     * 获取用户角色列表
     *
     * @return 角色列表
     */
    List<IRole> getRoles();

    /**
     * 获取用户权限列表
     *
     * @return 权限列表
     */
    List<IPermission> getPermissions();

    /**
     * 获取组织机构列表
     *
     * @return 组织机构列表
     */
    List<IOrganization> getOrganizations();
}
