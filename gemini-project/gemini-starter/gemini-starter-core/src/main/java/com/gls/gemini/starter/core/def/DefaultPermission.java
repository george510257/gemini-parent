package com.gls.gemini.starter.core.def;

import com.gls.gemini.common.core.base.BaseVo;
import com.gls.gemini.common.core.interfaces.IPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(title = "权限信息", description = "权限信息")
public class DefaultPermission extends BaseVo implements IPermission {

    @Schema(title = "权限名", description = "权限名")
    private String name;
    @Schema(title = "权限编码", description = "权限编码")
    private String code;
    @Schema(title = "权限描述", description = "权限描述")
    private String description;
    @Schema(title = "权限类型", description = "权限类型")
    private String type;
    @Schema(title = "父权限ID", description = "父权限ID")
    private Long parentId;
    @Schema(title = "权重", description = "权重")
    private Integer weight;
    @Schema(title = "资源指令", description = "资源指令")
    private String command;
}
