package com.gls.gemini.common.core.support;

import com.gls.gemini.common.core.domain.DefaultOrganization;
import com.gls.gemini.common.core.domain.DefaultPermission;
import com.gls.gemini.common.core.domain.DefaultRole;
import com.gls.gemini.common.core.domain.DefaultUser;

import java.util.Optional;

public class DefaultLoginTemplate implements LoginTemplate<DefaultUser, DefaultRole, DefaultPermission, DefaultOrganization> {
    @Override
    public Optional<DefaultUser> getLoginUser() {
        return Optional.empty();
    }
}
