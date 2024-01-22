package com.gls.gemini.common.bean.security.impl;

import com.gls.gemini.common.bean.security.IPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(title = "权限信息", description = "权限信息")
public class DefaultPermission implements IPermission<DefaultPermission> {

    @Schema(title = "权限ID", description = "权限ID")
    private Long id;
    @Schema(title = "权限名", description = "权限名")
    private String name;
    @Schema(title = "权限编码", description = "权限编码")
    private String code;
    @Schema(title = "权限描述", description = "权限描述")
    private String description;

    @Schema(title = "父权限ID", description = "父权限ID")
    private Long parentId;
    @Schema(title = "子权限列表", description = "子权限列表")
    private List<DefaultPermission> children;
    @Schema(title = "权重", description = "权重")
    private Integer weight;
}
