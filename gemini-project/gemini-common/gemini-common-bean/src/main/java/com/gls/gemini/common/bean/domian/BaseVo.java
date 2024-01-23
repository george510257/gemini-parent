package com.gls.gemini.common.bean.domian;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Schema(title = "基础实体", description = "基础实体")
public class BaseVo implements Serializable {

    @Schema(title = "主键", description = "主键")
    private Long id;
    @Schema(title = "删除标记 0:正常;1:已删除", description = "删除标记 0:正常;1:已删除")
    private Boolean deleted;
    @Schema(title = "创建人ID", description = "创建人ID")
    private Long createUserId;
    @Schema(title = "创建人姓名", description = "创建人姓名")
    private String createUserName;
    @Schema(title = "创建时间", description = "创建时间")
    private Date createTime;
    @Schema(title = "修改人ID", description = "修改人ID")
    private Long updateUserId;
    @Schema(title = "修改人姓名", description = "修改人姓名")
    private String updateUserName;
    @Schema(title = "更新时间", description = "更新时间")
    private Date updateTime;
}
