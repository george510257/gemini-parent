package com.gls.gemini.sdk.feishu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "feishu", contextId = "user", path = "/contact/v3/users")
public interface UserFeign {

    /**
     * 创建用户
     */
    @PostMapping
    void createUser();
}
