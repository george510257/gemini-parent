package com.gls.gemini.common.bean.security.impl;

import com.gls.gemini.common.bean.domian.BaseVo;
import com.gls.gemini.common.bean.security.IUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(title = "用户信息", description = "用户信息")
public class DefaultUser extends BaseVo implements IUser<DefaultRole, DefaultPermission, DefaultOrganization> {

    @Schema(title = "用户名", description = "用户名")
    private String username;
    @Schema(title = "密码", description = "密码")
    private String password;
    @Schema(title = "手机号", description = "手机号")
    private String mobile;
    @Schema(title = "邮箱", description = "邮箱")
    private String email;
    @Schema(title = "姓名", description = "姓名")
    private String realName;
    @Schema(title = "昵称", description = "昵称")
    private String nickname;
    @Schema(title = "头像", description = "头像")
    private String avatar;
    @Schema(title = "语言", description = "语言")
    private String language;
    @Schema(title = "国家", description = "国家")
    private Locale locale;
    @Schema(title = "时区", description = "时区")
    private TimeZone timeZone;

    @Schema(title = "是否启用", description = "是否启用")
    private boolean enabled = true;
    @Schema(title = "账号是否过期", description = "账号是否过期")
    private boolean accountNonExpired = true;
    @Schema(title = "账号是否锁定", description = "账号是否锁定")
    private boolean accountNonLocked = true;
    @Schema(title = "凭证是否过期", description = "凭证是否过期")
    private boolean credentialsNonExpired = true;

    @Schema(title = "当前角色", description = "当前角色")
    private DefaultRole role;
    @Schema(title = "当前组织", description = "当前组织")
    private DefaultOrganization organization;

    @Schema(title = "角色列表", description = "角色列表")
    private List<DefaultRole> roles;
    @Schema(title = "权限列表", description = "权限列表")
    private List<DefaultPermission> permissions;
    @Schema(title = "组织列表", description = "组织列表")
    private List<DefaultOrganization> organizations;

}
