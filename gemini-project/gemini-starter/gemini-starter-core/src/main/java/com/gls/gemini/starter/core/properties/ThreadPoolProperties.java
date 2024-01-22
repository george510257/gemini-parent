package com.gls.gemini.starter.core.properties;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".thread.pool")
public class ThreadPoolProperties extends BaseProperties {

    /**
     * 核心线程数
     */
    private Integer corePoolSize = CommonConstants.CPU_NUM;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize = CommonConstants.CPU_NUM * 2;
    /**
     * 队列容量
     */
    private Integer queueCapacity = 5000;
    /**
     * 线程池终止前的最大等待时间
     */
    private Integer awaitTerminationSeconds = 60;
    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "GEMINI-THREAD-";
}
