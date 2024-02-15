package com.gls.gemini.starter.mybatis.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gls.gemini.common.core.support.LoginTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * 基础实体处理器
 */
@Slf4j
@Component
public class BaseEntityHandler implements MetaObjectHandler {
    @Resource
    private Optional<LoginTemplate<?, ?, ?, ?>> loginTemplate;

    /**
     * 插入填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill metaObject: {}", metaObject);
        Date now = new Date();
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "createUserId", Long.class, loginTemplate.flatMap(LoginTemplate::getLoginUserId).orElse(0L));
        this.strictInsertFill(metaObject, "createUserName", String.class, loginTemplate.flatMap(LoginTemplate::getLoginUserName).orElse("system"));
        this.strictInsertFill(metaObject, "createTime", Date.class, now);
        this.strictInsertFill(metaObject, "updateUserId", Long.class, loginTemplate.flatMap(LoginTemplate::getLoginUserId).orElse(0L));
        this.strictInsertFill(metaObject, "updateUserName", String.class, loginTemplate.flatMap(LoginTemplate::getLoginUserName).orElse("system"));
        this.strictInsertFill(metaObject, "updateTime", Date.class, now);
    }

    /**
     * 更新填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill metaObject: {}", metaObject);
        Date now = new Date();
        this.strictUpdateFill(metaObject, "updateUserId", Long.class, loginTemplate.flatMap(LoginTemplate::getLoginUserId).orElse(0L));
        this.strictUpdateFill(metaObject, "updateUserName", String.class, loginTemplate.flatMap(LoginTemplate::getLoginUserName).orElse("system"));
        this.strictUpdateFill(metaObject, "updateTime", Date.class, now);
    }
}
