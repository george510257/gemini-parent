package com.gls.gemini.sdk.core.support;

import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.sdk.core.dto.OrganizationDto;
import com.gls.gemini.sdk.core.dto.PermissionDto;
import com.gls.gemini.sdk.core.dto.RoleDto;
import com.gls.gemini.sdk.core.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class DefaultLoginTemplate implements LoginTemplate<UserDto, RoleDto, PermissionDto, OrganizationDto> {
    @Override
    public Optional<UserDto> getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: {}", authentication);
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            log.info("principal: {}", principal);
            if (principal instanceof UserDto userDto) {
                return Optional.of(userDto);
            }
        }
        return Optional.empty();
    }
}
