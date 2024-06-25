package com.gls.gemini.starter.data.jpa.support;

import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * jpa雪花算法生成器
 */
public class JpaSnowflakeGenerator implements IdentifierGenerator {

    /**
     * 生成id
     *
     * @param session session
     * @param object  对象
     * @return id
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return IdUtil.getSnowflakeNextId();
    }
}
