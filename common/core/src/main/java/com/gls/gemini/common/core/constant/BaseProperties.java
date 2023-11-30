package com.gls.gemini.common.core.constant;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础属性
 */
@Data
public class BaseProperties implements Serializable {
    /**
     * 是否生效
     */
    private boolean enable = true;
    /**
     * 扩展属性
     */
    private Map<String, Object> extensions = new HashMap<>();
}
