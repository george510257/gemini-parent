package com.gls.gemini.starter.mybatis.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".mybatis")
public class MybatisProperties extends BaseProperties {
    /**
     * 租户配置
     */
    private Tenant tenant = new Tenant();

    @Data
    public static class Tenant implements Serializable {
        /**
         * 租户字段
         */
        private String column = "tenant_id";
        /**
         * 过滤表
         */
        private List<String> ignoreTable = new ArrayList<>();
    }
}
