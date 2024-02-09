package com.gls.gemini.starter.aliyun.oss.constants;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * oss 动态配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".aliyun.oss")
public class OssProperties extends BaseProperties {
    /**
     * 授权模式
     */
    private AuthorizationMode mode;
    /**
     * 阿里云OSS服务的Endpoint
     */
    private String endpoint;
    /**
     *
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * securityToken
     */
    private String securityToken;
    /**
     * 存储空间名称
     */
    private String bucketName;
    /**
     * 配置
     */
    @NestedConfigurationProperty
    private ClientBuilderConfiguration config = new ClientBuilderConfiguration();

    public enum AuthorizationMode {

        /**
         * 使用AK/SK
         */
        AK_SK,
        /**
         * 使用STS
         */
        STS
    }
}
