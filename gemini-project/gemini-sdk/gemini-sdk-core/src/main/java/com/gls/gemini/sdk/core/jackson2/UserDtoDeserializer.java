package com.gls.gemini.sdk.core.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.gemini.sdk.core.dto.OrganizationDto;
import com.gls.gemini.sdk.core.dto.RoleDto;
import com.gls.gemini.sdk.core.dto.UserDto;
import com.gls.gemini.sdk.core.support.DeserializerUtil;
import com.gls.gemini.starter.json.util.Jackson2Util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UserDtoDeserializer extends JsonDeserializer<UserDto> {
    @Override
    public UserDto deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        // 获取ObjectMapper
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        // 获取JsonNode
        JsonNode node = mapper.readTree(parser);
        // 获取JsonNode中的字段值
        // 用户名
        String username = Jackson2Util.findStringValue(node, "username");
        // 密码
        String password = Jackson2Util.findStringValue(node, "password");
        // 手机号
        String mobile = Jackson2Util.findStringValue(node, "mobile");
        // 邮箱
        String email = Jackson2Util.findStringValue(node, "email");
        // 姓名
        String realName = Jackson2Util.findStringValue(node, "realName");
        // 昵称
        String nickName = Jackson2Util.findStringValue(node, "nickName");
        // 头像
        String avatar = Jackson2Util.findStringValue(node, "avatar");
        // 语言
        String language = Jackson2Util.findStringValue(node, "language");
        // 国家
        Locale locale = Jackson2Util.findValue(node, "locale", Locale.class, mapper);
        // 时区
        TimeZone timeZone = Jackson2Util.findValue(node, "timeZone", TimeZone.class, mapper);
        // 当前角色
        RoleDto role = Jackson2Util.findValue(node, "role", RoleDto.class, mapper);
        // 当前组织
        OrganizationDto organization = Jackson2Util.findValue(node, "organization", OrganizationDto.class, mapper);
        // 角色列表
        List<RoleDto> roles = Jackson2Util.findValue(node, "roles", new TypeReference<>() {
        }, mapper);
        // 组织列表
        List<OrganizationDto> organizations = Jackson2Util.findValue(node, "organizations", new TypeReference<>() {
        }, mapper);
        // 构建UserDto对象
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setMobile(mobile);
        userDto.setEmail(email);
        userDto.setRealName(realName);
        userDto.setNickName(nickName);
        userDto.setAvatar(avatar);
        userDto.setLanguage(language);
        userDto.setLocale(locale);
        userDto.setTimeZone(timeZone);
        userDto.setRole(role);
        userDto.setOrganization(organization);
        userDto.setRoles(roles);
        userDto.setOrganizations(organizations);
        DeserializerUtil.setDomain(userDto, node, mapper);
        // 返回UserDto对象
        return userDto;
    }
}
