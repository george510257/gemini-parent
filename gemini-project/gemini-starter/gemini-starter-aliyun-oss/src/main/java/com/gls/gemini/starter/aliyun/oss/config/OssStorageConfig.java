package com.gls.gemini.starter.aliyun.oss.config;

import com.aliyun.oss.OSS;
import com.gls.gemini.starter.aliyun.oss.constants.OssProperties;
import com.gls.gemini.starter.aliyun.oss.storage.OssStorageProtocolResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 对象存储配置
 */
@Configuration
public class OssStorageConfig {
    /**
     * 对象存储协议解析器
     *
     * @param oss             oss客户端
     * @param ossTaskExecutor oss任务执行器
     * @return 对象存储协议解析器
     */
    @Bean
    @ConditionalOnMissingBean
    public OssStorageProtocolResolver ossStorageProtocolResolver(OSS oss, ExecutorService ossTaskExecutor) {
        return new OssStorageProtocolResolver(oss, ossTaskExecutor);
    }

    /**
     * 对象存储任务执行器
     *
     * @param ossProperties 对象存储配置
     * @return 对象存储任务执行器
     */
    @Bean
    @ConditionalOnMissingBean
    public ExecutorService ossTaskExecutor(OssProperties ossProperties) {
        OssProperties.ThreadPool threadPool = ossProperties.getThreadPool();
        return new ThreadPoolExecutor(threadPool.getCorePoolSize(), threadPool.getMaximumPoolSize(), threadPool.getKeepAliveTime(), threadPool.getUnit(), new SynchronousQueue<>());
    }
}
