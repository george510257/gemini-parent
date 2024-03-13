package com.gls.gemini.starter.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

@Component
@MappedTypes(Locale.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LocaleTypeHandler extends BaseTypeHandler<Locale> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Locale parameter, JdbcType jdbcType) throws SQLException {
        // 设置参数
        ps.setString(i, parameter.toLanguageTag());
    }

    @Override
    public Locale getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnName);
        if (result != null) {
            return Locale.forLanguageTag(result);
        }
        return null;
    }

    @Override
    public Locale getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnIndex);
        if (result != null) {
            return Locale.forLanguageTag(result);
        }
        return null;
    }

    @Override
    public Locale getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = cs.getString(columnIndex);
        if (result != null) {
            return Locale.forLanguageTag(result);
        }
        return null;
    }
}
