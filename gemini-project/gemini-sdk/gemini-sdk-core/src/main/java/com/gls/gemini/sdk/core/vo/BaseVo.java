package com.gls.gemini.sdk.core.vo;

import com.gls.gemini.common.core.interfaces.IDoMain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 基础实体
 */
@Data
@Schema(title = "基础实体", description = "基础实体")
public class BaseVo implements IDoMain {

    /**
     * 主键
     */
    @Schema(title = "主键", description = "主键")
    private Long id;

    /**
     * 租户ID
     */
    @Schema(title = "租户ID", description = "租户ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @Schema(title = "版本号", description = "版本号")
    private Integer version;

    /**
     * 删除标记 0:正常;1:已删除
     */
    @Schema(title = "删除标记 0:正常;1:已删除", description = "删除标记 0:正常;1:已删除")
    private Boolean deleted;

    /**
     * 创建人ID
     */
    @Schema(title = "创建人ID", description = "创建人ID")
    private Long createUserId;

    /**
     * 创建人姓名
     */
    @Schema(title = "创建人姓名", description = "创建人姓名")
    private String createUserName;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间", description = "创建时间")
    private Date createTime;

    /**
     * 修改人ID
     */
    @Schema(title = "修改人ID", description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改人姓名
     */
    @Schema(title = "修改人姓名", description = "修改人姓名")
    private String updateUserName;

    /**
     * 修改时间
     */
    @Schema(title = "更新时间", description = "更新时间")
    private Date updateTime;
}
