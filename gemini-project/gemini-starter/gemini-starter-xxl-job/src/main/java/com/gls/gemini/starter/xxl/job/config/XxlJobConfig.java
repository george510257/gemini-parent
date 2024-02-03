package com.gls.gemini.starter.xxl.job.config;

import com.gls.gemini.starter.xxl.job.constants.XxlJobProperties;
import com.gls.gemini.starter.xxl.job.support.XxlJobExecutorConverter;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 */
@Configuration
public class XxlJobConfig {

    /**
     * xxl-job执行器
     *
     * @param xxlJobProperties        xxl-job配置
     * @param xxlJobExecutorConverter xxl-job执行器转换器
     * @return xxl-job执行器
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties,
                                               XxlJobExecutorConverter xxlJobExecutorConverter) {
        return xxlJobExecutorConverter.convert(xxlJobProperties);
    }
}
