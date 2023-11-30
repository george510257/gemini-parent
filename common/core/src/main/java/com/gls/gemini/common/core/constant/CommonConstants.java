package com.gls.gemini.common.core.constant;

/**
 * 公共常量
 */
public interface CommonConstants {
    /**
     * 基础属性前缀
     */
    String PROPERTIES_PREFIX_BASE = "gemini";
    /**
     * CPU核心数
     */
    Integer CPU_NUM = Runtime.getRuntime().availableProcessors();
    /**
     * 默认数据源名称
     */
    String DEFAULT_DATASOURCE_NAME = "master";
}
