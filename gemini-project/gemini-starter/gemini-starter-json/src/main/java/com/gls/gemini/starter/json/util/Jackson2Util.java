package com.gls.gemini.starter.json.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;

/**
 * Jackson2 工具类
 */
@UtilityClass
public class Jackson2Util {
    /**
     * Set<String> 类型引用
     */
    public final TypeReference<Set<String>> SET_STRING_TYPE_REFERENCE = new TypeReference<>() {
    };
    /**
     * Map<String, Object> 类型引用
     */
    public final TypeReference<Map<String, Object>> MAP_STRING_OBJECT_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 查找 JsonNode 中的字段值
     *
     * @param jsonNode  JsonNode
     * @param fieldName 字段名
     * @return 字段值
     */
    public String findStringValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isTextual()) ? value.asText() : null;
    }

    /**
     * 查找 JsonNode 中的字段值
     *
     * @param jsonNode           JsonNode
     * @param fieldName          字段名
     * @param valueTypeReference 类型引用
     * @param mapper             ObjectMapper
     * @param <T>                字段值类型
     * @return 字段值
     */
    public <T> T findValue(JsonNode jsonNode, String fieldName, TypeReference<T> valueTypeReference,
                           ObjectMapper mapper) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isContainerNode()) ? mapper.convertValue(value, valueTypeReference) : null;
    }

    /**
     * 查找 JsonNode 中的字段值
     *
     * @param jsonNode  JsonNode
     * @param fieldName 字段名
     * @param valueType 字段值类型
     * @param mapper    ObjectMapper
     * @param <T>       字段值类型
     * @return 字段值
     */
    public <T> T findValue(JsonNode jsonNode, String fieldName, Class<T> valueType, ObjectMapper mapper) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isContainerNode()) ? mapper.convertValue(value, valueType) : null;
    }

    /**
     * 查找 JsonNode 中的对象节点
     *
     * @param jsonNode  JsonNode
     * @param fieldName 字段名
     * @return 对象节点
     */
    public JsonNode findObjectNode(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isObject()) ? value : null;
    }

}
