package com.gls.gemini.sdk.core.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.gemini.sdk.core.dto.PermissionDto;
import com.gls.gemini.sdk.core.dto.RoleDto;
import com.gls.gemini.sdk.core.support.DeserializerUtil;
import com.gls.gemini.starter.json.util.Jackson2Util;

import java.io.IOException;
import java.util.List;

public class RoleDtoDeserializer extends JsonDeserializer<RoleDto> {
    @Override
    public RoleDto deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        // 获取ObjectMapper
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        // 获取JsonNode
        JsonNode node = mapper.readTree(parser);
        // 获取JsonNode中的字段值
        // 角色名
        String name = Jackson2Util.findStringValue(node, "name");
        // 角色编码
        String code = Jackson2Util.findStringValue(node, "code");
        // 角色描述
        String description = Jackson2Util.findStringValue(node, "description");
        // 角色类型
        String type = Jackson2Util.findStringValue(node, "type");
        // 父角色ID
        Long parentId = Jackson2Util.findValue(node, "parentId", Long.class, mapper);
        // 排序
        Integer sort = Jackson2Util.findValue(node, "sort", Integer.class, mapper);
        // 权限列表
        List<PermissionDto> permissions = Jackson2Util.findValue(node, "permissions", new TypeReference<>() {
        }, mapper);

        // 构建RoleDto对象
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        roleDto.setCode(code);
        roleDto.setDescription(description);
        roleDto.setType(type);
        roleDto.setParentId(parentId);
        roleDto.setSort(sort);
        roleDto.setPermissions(permissions);
        DeserializerUtil.setDomain(roleDto, node, mapper);
        // 返回RoleDto对象
        return roleDto;
    }
}
