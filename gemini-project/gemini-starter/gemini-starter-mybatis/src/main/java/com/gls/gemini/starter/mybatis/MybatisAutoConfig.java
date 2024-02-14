package com.gls.gemini.starter.mybatis;

import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.mybatis.constants.MybatisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@MapperScan(basePackages = CommonConstants.BASE_PACKAGE_PREFIX + ".**.mapper")
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisAutoConfig {
}
