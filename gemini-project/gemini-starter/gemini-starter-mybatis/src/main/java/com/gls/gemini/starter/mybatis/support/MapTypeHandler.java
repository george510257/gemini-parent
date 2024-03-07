package com.gls.gemini.starter.mybatis.support;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Map类型处理器
 *
 * @param <K> key
 * @param <V> value
 */
@MappedTypes(Map.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MapTypeHandler<K, V> extends BaseTypeHandler<Map<K, V>> {
    /**
     * 设置非空参数
     *
     * @param ps        数据库操作对象
     * @param i         参数索引
     * @param parameter 参数
     * @param jdbcType  jdbc类型
     * @throws SQLException sql异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<K, V> parameter, JdbcType jdbcType) throws SQLException {
        // map转json字符串
        ps.setString(i, JSONUtil.toJsonStr(parameter));
    }

    /**
     * 获取可空结果
     *
     * @param rs         结果集
     * @param columnName 列名
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Map<K, V> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 获取json字符串
        String jsonStr = rs.getString(columnName);
        if (jsonStr != null) {
            // json字符串转map
            return JSONUtil.toBean(jsonStr, new TypeReference<>() {
            }, true);
        }
        return null;
    }

    /**
     * 获取可空结果
     *
     * @param rs          结果集
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Map<K, V> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 获取json字符串
        String jsonStr = rs.getString(columnIndex);
        if (jsonStr != null) {
            // json字符串转map
            return JSONUtil.toBean(jsonStr, new TypeReference<>() {
            }, true);
        }
        return null;
    }

    /**
     * 获取可空结果
     *
     * @param cs          存储过程
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Map<K, V> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 获取json字符串
        String jsonStr = cs.getString(columnIndex);
        if (jsonStr != null) {
            // json字符串转map
            return JSONUtil.toBean(jsonStr, new TypeReference<>() {
            }, true);
        }
        return null;
    }
}
