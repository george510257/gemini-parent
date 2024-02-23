package com.gls.gemini.sdk.feishu;

import com.gls.gemini.sdk.feishu.constants.FeishuProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({FeishuProperties.class})
public class FeishuAutoConfig {
}
