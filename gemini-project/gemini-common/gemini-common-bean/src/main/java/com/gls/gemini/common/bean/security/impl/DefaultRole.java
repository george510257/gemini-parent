package com.gls.gemini.common.bean.security.impl;

import com.gls.gemini.common.bean.security.IRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(title = "角色信息", description = "角色信息")
public class DefaultRole implements IRole<DefaultRole> {

    @Schema(title = "角色ID", description = "角色ID")
    private Long id;
    @Schema(title = "角色名", description = "角色名")
    private String name;
    @Schema(title = "角色编码", description = "角色编码")
    private String code;
    @Schema(title = "角色描述", description = "角色描述")
    private String description;
    @Schema(title = "父角色ID", description = "父角色ID")

    private Long parentId;
    @Schema(title = "子角色列表", description = "子角色列表")
    private List<DefaultRole> children;
    @Schema(title = "权重", description = "权重")
    private Integer weight;

}
