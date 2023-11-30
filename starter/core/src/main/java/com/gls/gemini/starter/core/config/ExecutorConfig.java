package com.gls.gemini.starter.core.config;

import com.gls.gemini.starter.core.properties.ExecutorThreadPoolProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@AutoConfiguration
public class ExecutorConfig implements AsyncConfigurer {
    @Resource
    private ExecutorThreadPoolProperties executorThreadPoolProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认区 CPU 数量
        taskExecutor.setCorePoolSize(executorThreadPoolProperties.getCorePoolSize());
        // 最大线程大小 默认区 CPU * 2 数量
        taskExecutor.setMaxPoolSize(executorThreadPoolProperties.getMaxPoolSize());
        // 队列最大容量
        taskExecutor.setQueueCapacity(executorThreadPoolProperties.getQueueCapacity());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(executorThreadPoolProperties.getAwaitTerminationSeconds());
        taskExecutor.setThreadNamePrefix(executorThreadPoolProperties.getThreadNamePrefix());
        taskExecutor.initialize();
        return taskExecutor;
    }
}