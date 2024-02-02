package com.gls.gemini.starter.data.jpa.support;

import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.starter.data.jpa.base.BaseEntity;
import jakarta.annotation.Resource;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DefaultEntityListener {

    @Resource
    private LoginTemplate<?, ?, ?, ?> loginTemplate;

    /**
     * 新增前操作
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        log.info("prePersist entity: {}", entity);
        Date now = new Date();
        entity.setDeleted(false);
        entity.setCreateUserId(loginTemplate.getLoginUserId().orElse(0L));
        entity.setCreateUserName(loginTemplate.getLoginUserName().orElse("system"));
        entity.setCreateTime(now);
        entity.setUpdateUserId(loginTemplate.getLoginUserId().orElse(0L));
        entity.setUpdateUserName(loginTemplate.getLoginUserName().orElse("system"));
        entity.setUpdateTime(now);
    }

    /**
     * 更新前操作
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        log.info("preUpdate entity: {}", entity);
        Date now = new Date();
        entity.setUpdateUserId(loginTemplate.getLoginUserId().orElse(0L));
        entity.setUpdateUserName(loginTemplate.getLoginUserName().orElse("system"));
        entity.setUpdateTime(now);
    }
}
