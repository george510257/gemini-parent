package com.gls.gemini.sdk.core.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.gemini.sdk.core.vo.BaseVo;
import com.gls.gemini.starter.json.util.Jackson2Util;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DeserializerUtil {

    public <T extends BaseVo> void setDomain(T t, JsonNode node, ObjectMapper mapper) {
        // 主键
        Long id = Jackson2Util.findValue(node, "id", Long.class, mapper);
        // 租户ID
        Long tenantId = Jackson2Util.findValue(node, "tenantId", Long.class, mapper);
        // 版本号
        Integer version = Jackson2Util.findValue(node, "version", Integer.class, mapper);
        // 删除标记 0:正常;1:已删除
        Boolean deleted = Jackson2Util.findValue(node, "deleted", Boolean.class, mapper);
        // 创建人ID
        Long createUserId = Jackson2Util.findValue(node, "createUserId", Long.class, mapper);
        // 创建人姓名
        String createUserName = Jackson2Util.findStringValue(node, "createUserName");
        // 创建时间
        Date createTime = Jackson2Util.findValue(node, "createTime", Date.class, mapper);
        // 修改人ID
        Long updateUserId = Jackson2Util.findValue(node, "updateUserId", Long.class, mapper);
        // 修改人姓名
        String updateUserName = Jackson2Util.findStringValue(node, "updateUserName");
        // 修改时间
        Date updateTime = Jackson2Util.findValue(node, "updateTime", Date.class, mapper);
        t.setId(id);
        t.setTenantId(tenantId);
        t.setVersion(version);
        t.setDeleted(deleted);
        t.setCreateUserId(createUserId);
        t.setCreateUserName(createUserName);
        t.setCreateTime(createTime);
        t.setUpdateUserId(updateUserId);
        t.setUpdateUserName(updateUserName);
        t.setUpdateTime(updateTime);
    }
}
