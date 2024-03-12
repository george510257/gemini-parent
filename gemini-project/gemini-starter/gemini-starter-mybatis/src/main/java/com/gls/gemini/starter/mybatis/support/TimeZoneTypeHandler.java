package com.gls.gemini.starter.mybatis.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

@Component
@MappedTypes(TimeZone.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TimeZoneTypeHandler extends BaseTypeHandler<TimeZone> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TimeZone parameter, JdbcType jdbcType) throws SQLException {
        // 设置参数
        ps.setString(i, parameter.getID());
    }

    @Override
    public TimeZone getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnName);
        if (result != null) {
            return TimeZone.getTimeZone(result);
        }
        return null;
    }

    @Override
    public TimeZone getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnIndex);
        if (result != null) {
            return TimeZone.getTimeZone(result);
        }
        return null;
    }

    @Override
    public TimeZone getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = cs.getString(columnIndex);
        if (result != null) {
            return TimeZone.getTimeZone(result);
        }
        return null;
    }
}
