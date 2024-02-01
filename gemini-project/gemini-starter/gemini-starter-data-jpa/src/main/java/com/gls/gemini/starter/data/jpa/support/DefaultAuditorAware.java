package com.gls.gemini.starter.data.jpa.support;

import cn.hutool.extra.spring.SpringUtil;
import com.gls.gemini.common.core.support.LoginTemplate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 默认审计者
 */
@Component
public class DefaultAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        LoginTemplate<?, ?, ?, ?> loginTemplate = SpringUtil.getBean(LoginTemplate.class);
        return loginTemplate.getLoginUserId();
    }
}
