package com.gls.gemini.common.bean.user.impl;

import com.gls.gemini.common.bean.user.IUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Data
@Accessors(chain = true)
@Schema(title = "用户信息", description = "用户信息")
public class DefaultUser implements IUser {

    @Schema(title = "用户ID", description = "用户ID")
    private Long id;
    @Schema(title = "用户名", description = "用户名")
    private String username;
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
    @Schema(title = "角色列表", description = "角色列表")
    private List<DefaultRole> roles;
    @Schema(title = "权限列表", description = "权限列表")
    private List<DefaultPermission> permissions;
    @Schema(title = "组织列表", description = "组织列表")
    private List<DefaultOrganization> organizations;
}
