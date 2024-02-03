package com.gls.gemini.starter.xxl.job.support;

import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.starter.xxl.job.constants.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface XxlJobExecutorConverter extends BaseConverter<XxlJobProperties, XxlJobSpringExecutor> {
}
