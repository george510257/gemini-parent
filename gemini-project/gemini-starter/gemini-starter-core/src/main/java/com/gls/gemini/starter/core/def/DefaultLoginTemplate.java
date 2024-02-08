package com.gls.gemini.starter.core.def;

import com.gls.gemini.common.core.support.LoginTemplate;

import java.util.Optional;

public class DefaultLoginTemplate implements LoginTemplate<DefaultUser, DefaultRole, DefaultPermission, DefaultOrganization> {
    @Override
    public Optional<DefaultUser> getLoginUser() {
        return Optional.empty();
    }
}
