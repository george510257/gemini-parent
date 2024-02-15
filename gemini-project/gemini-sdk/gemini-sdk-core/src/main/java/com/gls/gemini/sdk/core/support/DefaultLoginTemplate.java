package com.gls.gemini.sdk.core.support;

import com.gls.gemini.common.core.support.LoginTemplate;
import com.gls.gemini.sdk.core.vo.OrganizationVo;
import com.gls.gemini.sdk.core.vo.PermissionVo;
import com.gls.gemini.sdk.core.vo.RoleVo;
import com.gls.gemini.sdk.core.vo.UserVo;

import java.util.Optional;

public class DefaultLoginTemplate implements LoginTemplate<UserVo, RoleVo, PermissionVo, OrganizationVo> {
    @Override
    public Optional<UserVo> getLoginUser() {
        return Optional.empty();
    }
}
