package com.gls.gemini.sdk.core.support;

import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.sdk.core.vo.OrganizationVo;
import com.gls.gemini.sdk.core.vo.PermissionVo;
import com.gls.gemini.sdk.core.vo.RoleVo;
import com.gls.gemini.sdk.core.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class DefaultLoginTemplate implements LoginTemplate<UserVo, RoleVo, PermissionVo, OrganizationVo> {
    @Override
    public Optional<UserVo> getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: {}", authentication);
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            log.info("principal: {}", principal);
            if (principal instanceof UserVo userVo) {
                return Optional.of(userVo);
            }
        }
        return Optional.empty();
    }
}
