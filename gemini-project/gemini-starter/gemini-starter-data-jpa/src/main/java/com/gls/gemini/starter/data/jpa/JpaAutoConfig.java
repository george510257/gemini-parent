package com.gls.gemini.starter.data.jpa;

import com.gls.gemini.common.core.constant.CommonConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EntityScan(basePackages = CommonConstants.BASE_PACKAGE_PREFIX + ".**.entity")
@EnableJpaRepositories(basePackages = CommonConstants.BASE_PACKAGE_PREFIX + ".**.repository")
public class JpaAutoConfig {
}
