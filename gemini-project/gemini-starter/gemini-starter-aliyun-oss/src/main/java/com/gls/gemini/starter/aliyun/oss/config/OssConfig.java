package com.gls.gemini.starter.aliyun.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gls.gemini.starter.aliyun.oss.constants.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

/**
 * oss 配置
 */
@Configuration
public class OssConfig {

    /**
     * ossClient
     *
     * @param ossProperties oss配置
     * @return ossClient
     */
    @Bean
    @ConditionalOnMissingBean
    public OSS ossClient(OssProperties ossProperties) {
        OssProperties.Client client = ossProperties.getClient();
        if (client.getMode() == OssProperties.Client.Mode.AK_SK) {
            return new OSSClientBuilder()
                    .build(client.getEndpoint(),
                            client.getToken().getAccessKey(),
                            client.getToken().getSecretKey(),
                            client.getConfig());
        } else if (client.getMode() == OssProperties.Client.Mode.STS) {
            return new OSSClientBuilder()
                    .build(client.getEndpoint(),
                            client.getStsToken().getAccessKey(),
                            client.getStsToken().getSecretKey(),
                            client.getStsToken().getSecurityToken(),
                            client.getConfig());
        } else {
            throw new IllegalArgumentException("Unknown auth mode.");
        }
    }

    /**
     * 关闭ossClient
     *
     * @param event 事件
     */
    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent event) {
        // 关闭ossClient
        Map<String, OSS> beansOfType = event.getApplicationContext().getBeansOfType(OSS.class);
        beansOfType.values().forEach(OSS::shutdown);
    }
}
