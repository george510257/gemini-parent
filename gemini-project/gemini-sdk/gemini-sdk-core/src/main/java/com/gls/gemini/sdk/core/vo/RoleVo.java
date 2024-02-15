package com.gls.gemini.sdk.core.vo;

import com.gls.gemini.common.core.interfaces.IRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "角色信息", description = "角色信息")
public class RoleVo extends BaseVo implements IRole {

    @Schema(title = "角色名", description = "角色名")
    private String name;
    @Schema(title = "角色编码", description = "角色编码")
    private String code;
    @Schema(title = "角色描述", description = "角色描述")
    private String description;
    @Schema(title = "角色类型", description = "角色类型")
    private String type;
    @Schema(title = "父角色ID", description = "父角色ID")
    private Long parentId;
    @Schema(title = "权重", description = "权重")
    private Integer weight;
}
