package com.gls.gemini.starter.mybatis.support;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.starter.mybatis.constants.MybatisProperties;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

@Component
public class DefaultTenantLineHandler implements TenantLineHandler {
    @Resource
    private MybatisProperties mybatisProperties;

    @Override
    public Expression getTenantId() {
        Long tenantId = LoginTemplate.getLoginTenantId().orElse(0L);
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return mybatisProperties.getTenant().getColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return mybatisProperties.getTenant().getIgnoreTable().contains(tableName);
    }
}
