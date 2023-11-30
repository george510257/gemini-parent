package com.gls.gemini.starter.core.config;

import com.gls.gemini.starter.core.properties.ThreadPoolProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
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
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(threadPoolProperties.getAwaitTerminationSeconds());
        taskExecutor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
