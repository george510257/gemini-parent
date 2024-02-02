package com.gls.gemini.starter.data.jpa.base;

import com.gls.gemini.starter.data.jpa.support.DefaultEntityListener;
import com.gls.gemini.starter.data.jpa.support.JpaSnowflakeGenerator;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners({DefaultEntityListener.class})
public abstract class BaseEntity implements Serializable {

    @Id
    @GenericGenerator(name = "snowflake", type = JpaSnowflakeGenerator.class)
    @GeneratedValue(generator = "snowflake")
    @Comment("主键")
    private Long id;
    @Comment("删除标记 0:正常;1:已删除")
    private Boolean deleted;
    @Comment("创建人ID")
    private Long createUserId;
    @Comment("创建人姓名")
    private String createUserName;
    @Comment("创建时间")
    private Date createTime;
    @Comment("修改人ID")
    private Long updateUserId;
    @Comment("修改人姓名")
    private String updateUserName;
    @Comment("更新时间")
    private Date updateTime;
}
