package com.gls.gemini.starter.dynamic.datasource.support;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.gls.gemini.common.core.constant.CommonConstants;
import com.gls.gemini.starter.dynamic.datasource.properties.DataSourceProviderProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据源中获取 配置信息
 */
public class JdbcDataSourceProvider extends AbstractJdbcDataSourceProvider {
    /**
     * 数据源配置
     */
    private final DataSourceProperties dataSourceProperties;
    /**
     * 数据源提供者配置
     */
    private final DataSourceProviderProperties dataSourceProviderProperties;

    /**
     * 构造函数
     *
     * @param defaultDataSourceCreator     默认数据源创建器
     * @param dataSourceProperties         数据源配置
     * @param dataSourceProviderProperties 数据源提供者配置
     */
    public JdbcDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator, DataSourceProperties dataSourceProperties, DataSourceProviderProperties dataSourceProviderProperties) {
        super(defaultDataSourceCreator, dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        this.dataSourceProviderProperties = dataSourceProviderProperties;
        this.dataSourceProperties = dataSourceProperties;
    }

    /**
     * 执行语句获得数据源参数
     *
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        // 查询数据源
        ResultSet rs = statement.executeQuery(dataSourceProviderProperties.getQueryDsSql());
        // 转换为数据源配置
        Map<String, DataSourceProperty> dataSourcePropertiesMap = toDataSourcePropertiesMap(rs);
        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setDriverClassName(dataSourceProperties.getDriverClassName());
        property.setUrl(dataSourceProperties.getUrl());
        property.setUsername(dataSourceProperties.getUsername());
        property.setPassword(dataSourceProperties.getPassword());
        property.setLazy(true);
        dataSourcePropertiesMap.put(CommonConstants.DEFAULT_DATASOURCE_NAME, property);
        return dataSourcePropertiesMap;
    }

    /**
     * 转换为数据源配置
     *
     * @param rs 结果集
     * @return 数据源配置
     * @throws SQLException sql异常
     */
    private Map<String, DataSourceProperty> toDataSourcePropertiesMap(ResultSet rs) throws SQLException {
        // 转换为数据源配置
        Map<String, DataSourceProperty> dataSourcePropertiesMap = new HashMap<>();
        while (rs.next()) {
            String name = rs.getString(dataSourceProviderProperties.getDsNameColumn());
            DataSourceProperty property = new DataSourceProperty();
            property.setDriverClassName(rs.getString(dataSourceProviderProperties.getDsDriverColumn()));
            property.setUrl(rs.getString(dataSourceProviderProperties.getDsUrlColumn()));
            property.setUsername(rs.getString(dataSourceProviderProperties.getDsUsernameColumn()));
            property.setPassword(rs.getString(dataSourceProviderProperties.getDsPasswordColumn()));
            property.setLazy(true);
            dataSourcePropertiesMap.put(name, property);
        }
        return dataSourcePropertiesMap;
    }
}
