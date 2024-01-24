package com.gls.gemini.starter.web.result;

import com.gls.gemini.common.core.constant.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果忽略配置
 * <p>
 * 返回结果忽略配置，用于配置返回结果中需要忽略的字段
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".result.ignore")
public class ResultIgnoreProperties extends BaseProperties {

    /**
     * 忽略的返回类型
     */
    private List<Class<?>> returnType = new ArrayList<>();
    /**
     * 忽略的转换类型
     */
    private List<Class<?>> converterType = new ArrayList<>();
}
