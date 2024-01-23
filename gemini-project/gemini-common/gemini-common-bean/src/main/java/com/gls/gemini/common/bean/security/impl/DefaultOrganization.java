package com.gls.gemini.common.bean.security.impl;

import com.gls.gemini.common.bean.security.IOrganization;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "组织信息", description = "组织信息")
public class DefaultOrganization implements IOrganization {

    @Schema(title = "组织ID", description = "组织ID")
    private Long id;
    @Schema(title = "组织名", description = "组织名")
    private String name;
    @Schema(title = "组织编码", description = "组织编码")
    private String code;
    @Schema(title = "组织描述", description = "组织描述")
    private String description;
    @Schema(title = "组织类型", description = "组织类型")
    private String type;
    @Schema(title = "父组织ID", description = "父组织ID")
    private Long parentId;
    @Schema(title = "权重", description = "权重")
    private Integer weight;
}
