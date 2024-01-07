package com.gls.gemini.starter.dynamic.datasource.properties;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态数据源配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.PROPERTIES_PREFIX_BASE + ".dynamic.datasource.provider")
public class DataSourceProviderProperties extends BaseProperties {

    /**
     * 数据源名称列
     */
    private String dsNameColumn = "name";
    /**
     * 用户名列
     */
    private String dsUsernameColumn = "username";
    /**
     * 密码列
     */
    private String dsPasswordColumn = "password";
    /**
     * 数据源url列
     */
    private String dsUrlColumn = "url";
    /**
     * 数据源驱动类列
     */
    private String dsDriverColumn = "driver_class_name";
    /**
     * 查询数据源的SQL
     */
    private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";
}
