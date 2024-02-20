package com.gls.gemini.starter.data.jpa.support;

import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.starter.data.jpa.base.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DefaultEntityListener {

    /**
     * 新增前操作
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        log.info("prePersist entity: {}", entity);
        Date now = new Date();
        entity.setDeleted(false);
        entity.setCreateUserId(LoginTemplate.getLoginUserId().orElse(0L));
        entity.setCreateUserName(LoginTemplate.getLoginUserRealName().orElse("system"));
        entity.setCreateTime(now);
        entity.setUpdateUserId(LoginTemplate.getLoginUserId().orElse(0L));
        entity.setUpdateUserName(LoginTemplate.getLoginUserRealName().orElse("system"));
        entity.setUpdateTime(now);
    }

    /**
     * 更新前操作
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        log.info("preUpdate entity: {}", entity);
        Date now = new Date();
        entity.setUpdateUserId(LoginTemplate.getLoginUserId().orElse(0L));
        entity.setUpdateUserName(LoginTemplate.getLoginUserRealName().orElse("system"));
        entity.setUpdateTime(now);
    }
}
