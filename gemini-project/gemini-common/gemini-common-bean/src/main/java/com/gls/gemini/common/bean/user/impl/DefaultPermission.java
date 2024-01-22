package com.gls.gemini.common.bean.user.impl;

import com.gls.gemini.common.bean.user.IPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "权限信息", description = "权限信息")
public class DefaultPermission implements IPermission {

    @Schema(title = "权限ID", description = "权限ID")
    private Long id;
    @Schema(title = "权限名", description = "权限名")
    private String name;
    @Schema(title = "权限编码", description = "权限编码")
    private String code;
    @Schema(title = "权限描述", description = "权限描述")
    private String description;
}
