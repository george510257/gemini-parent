package com.gls.gemini.starter.core.config;

import com.gls.gemini.starter.core.properties.ThreadPoolProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@EnableAsync
@AutoConfiguration
public class ExecutorConfig implements AsyncConfigurer {
    /**
     * 线程池配置
     */
    @Resource
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 获取异步线程池
     *
     * @return Executor
     */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认区 CPU 数量
        taskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 最大线程大小 默认区 CPU * 2 数量
        taskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 队列最大容量
        taskExecutor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 拒绝策略 默认为 CallerRunsPolicy 策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时长
        taskExecutor.setAwaitTerminationSeconds(threadPoolProperties.getAwaitTerminationSeconds());
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
