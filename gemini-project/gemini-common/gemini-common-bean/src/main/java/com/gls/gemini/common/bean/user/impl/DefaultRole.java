package com.gls.gemini.common.bean.user.impl;

import com.gls.gemini.common.bean.user.IRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "角色信息", description = "角色信息")
public class DefaultRole implements IRole {

    @Schema(title = "角色ID", description = "角色ID")
    private Long id;
    @Schema(title = "角色名", description = "角色名")
    private String name;
    @Schema(title = "角色编码", description = "角色编码")
    private String code;
    @Schema(title = "角色描述", description = "角色描述")
    private String description;
}
