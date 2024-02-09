package com.gls.gemini.starter.aliyun.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gls.gemini.starter.aliyun.oss.constants.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oss 配置
 */
@Configuration
public class OssConfig {

    @Bean
    @ConditionalOnMissingBean(OSS.class)
    public OSS ossClient(OssProperties ossProperties) {
        if (ossProperties.getMode() == OssProperties.AuthorizationMode.AK_SK) {
            return new OSSClientBuilder()
                    .build(ossProperties.getEndpoint(),
                            ossProperties.getAccessKey(),
                            ossProperties.getSecretKey(),
                            ossProperties.getConfig());
        } else if (ossProperties.getMode() == OssProperties.AuthorizationMode.STS) {
            return new OSSClientBuilder()
                    .build(ossProperties.getEndpoint(),
                            ossProperties.getAccessKey(),
                            ossProperties.getSecretKey(),
                            ossProperties.getSecurityToken(),
                            ossProperties.getConfig());
        } else {
            throw new IllegalArgumentException("Unknown auth mode.");
        }
    }
}
