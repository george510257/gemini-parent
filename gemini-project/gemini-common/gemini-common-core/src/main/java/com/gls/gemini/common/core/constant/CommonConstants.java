package com.gls.gemini.common.core.constant;

/**
 * 公共常量
 */
public interface CommonConstants {
    /**
     * 基础属性前缀
     */
    String BASE_PROPERTIES_PREFIX = "gemini";
    /**
     * 基础包前缀
     */
    String BASE_PACKAGE_PREFIX = "com.gls.gemini";
    /**
     * CPU核心数
     */
    Integer CPU_NUM = Runtime.getRuntime().availableProcessors();
    /**
     * 默认数据源名称
     */
    String DEFAULT_DATASOURCE_NAME = "master";

    /**
     * 默认网关服务ID
     */
    String GATEWAY_SERVICE_ID = "gemini-gateway";
    /**
     * 默认网关服务名称
     */
    String GATEWAY_SERVICE_NAME = "网关服务";
    /**
     * 默认认证授权服务ID
     */
    String UAA_SERVICE_ID = "gemini-uaa";
    /**
     * 默认认证授权服务名称
     */
    String UAA_SERVICE_NAME = "认证授权服务";
    /**
     * 默认用户权限服务ID
     */
    String UPMS_SERVICE_ID = "gemini-upms";
    /**
     * 默认用户权限服务名称
     */
    String UPMS_SERVICE_NAME = "用户权限服务";
    /**
     * 默认用户ID
     */
    Long DEFAULT_USER_ID = 0L;
    /**
     * 默认用户名
     */
    String DEFAULT_USER_USERNAME = "admin";
    /**
     * 默认用户密码
     */
    String DEFAULT_USER_PASSWORD = "123456";
    /**
     * jwk set uri
     */
    String JWK_SET_URI = "/oauth2/jwks";

}
