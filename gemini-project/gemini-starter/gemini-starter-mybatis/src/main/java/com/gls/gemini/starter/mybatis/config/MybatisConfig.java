package com.gls.gemini.starter.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

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
    @ConditionalOnBean(InnerInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> innerInterceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        innerInterceptors.forEach(interceptor::addInnerInterceptor);
        return interceptor;
    }

    /**
     * 自定义SqlSessionFactoryBean
     *
     * @return 自定义SqlSessionFactoryBean
     */
    @Bean
    @ConditionalOnBean(TypeHandler.class)
    public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer(List<TypeHandler<?>> typeHandlers) {
        return (sqlSessionFactoryBean) -> {
            sqlSessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler<?>[0]));
        };
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁插件
     */
    @Bean
    @Order(1)
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    @Order(10)
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    /**
     * 租户插件
     *
     * @return 租户插件
     */
    @Bean
    @Order(3)
    @ConditionalOnBean(TenantLineHandler.class)
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantLineHandler) {
        return new TenantLineInnerInterceptor(tenantLineHandler);
    }

    /**
     * 数据权限插件
     *
     * @return 数据权限插件
     */
    @Bean
    @Order(4)
    @ConditionalOnBean(DataPermissionHandler.class)
    public DataPermissionInterceptor dataPermissionInterceptor(DataPermissionHandler dataPermissionHandler) {
        return new DataPermissionInterceptor(dataPermissionHandler);
    }
}
