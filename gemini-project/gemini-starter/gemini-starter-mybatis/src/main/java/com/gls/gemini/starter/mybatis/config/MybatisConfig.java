package com.gls.gemini.starter.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.gls.gemini.starter.mybatis.support.MapTypeHandler;
import com.gls.gemini.starter.mybatis.support.SetTypeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置
 */
@Configuration
public class MybatisConfig {
    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineHandler tenantLineHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 添加数据权限插件
//        interceptor.addInnerInterceptor(new DataPermissionInterceptor());
        // 添加租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler));
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自定义SqlSessionFactoryBean
     *
     * @return 自定义SqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer() {
        return (sqlSessionFactoryBean) -> {
            MapTypeHandler<?, ?> mapTypeHandler = new MapTypeHandler<>();
            SetTypeHandler<?> setTypeHandler = new SetTypeHandler<>();
            sqlSessionFactoryBean.setTypeHandlers(mapTypeHandler, setTypeHandler);
        };
    }
}
