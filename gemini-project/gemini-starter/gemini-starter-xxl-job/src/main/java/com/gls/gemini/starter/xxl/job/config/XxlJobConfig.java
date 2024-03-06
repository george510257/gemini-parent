package com.gls.gemini.starter.xxl.job.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.gls.gemini.starter.xxl.job.constants.XxlJobConstants;
import com.gls.gemini.starter.xxl.job.constants.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

/**
 * 定时任务配置
 */
@Slf4j
@Configuration
public class XxlJobConfig {

    /**
     * xxl-job执行器
     *
     * @param xxlJobProperties xxl-job配置
     * @param discoveryClient  服务发现客户端
     * @return xxl-job执行器
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties, DiscoveryClient discoveryClient) {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        // 定时任务中心地址
        String adminAddresses = discoveryClient.getServices().stream()
                .filter(serviceId -> serviceId.contains(XxlJobConstants.ADMIN_SERVICE_ID))
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                .map(serviceInstance -> serviceInstance.getUri().toString() + "/" + XxlJobConstants.ADMIN_SERVICE_ID)
                .collect(Collectors.joining(","));
        log.info("定时任务中心地址: {}", adminAddresses);
        if (StrUtil.isNotBlank(adminAddresses)) {
            executor.setAdminAddresses(adminAddresses);
        } else {
            executor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        }
        // 执行器AppName
        if (xxlJobProperties.getAppName() != null) {
            executor.setAppname(xxlJobProperties.getAppName());
        } else {
            executor.setAppname(SpringUtil.getApplicationName());
        }
        // 执行器注册地址
        if (xxlJobProperties.getAddress() != null) {
            executor.setAddress(xxlJobProperties.getAddress());
        }
        // 执行器IP
        if (xxlJobProperties.getIp() != null) {
            executor.setIp(xxlJobProperties.getIp());
        }
        // 执行器端口号
        if (xxlJobProperties.getPort() != 0) {
            executor.setPort(xxlJobProperties.getPort());
        }
        // 执行器Token
        if (xxlJobProperties.getAccessToken() != null) {
            executor.setAccessToken(xxlJobProperties.getAccessToken());
        }
        // 执行器运行日志文件存储磁盘路径
        if (xxlJobProperties.getLogPath() != null) {
            executor.setLogPath(xxlJobProperties.getLogPath());
        }
        // 执行器日志保存天数
        if (xxlJobProperties.getLogRetentionDays() != 0) {
            executor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        }
        return executor;
    }
}
