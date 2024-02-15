package com.gls.gemini.common.core.interfaces;

import java.io.Serializable;
import java.util.Date;

/**
 * 领域对象
 */
public interface IDomain extends Serializable {

    /**
     * 获取ID
     */
    Long getId();

    /**
     * 设置ID
     */
    void setId(Long id);

    /**
     * 获取租户ID
     */
    Long getTenantId();

    /**
     * 设置租户ID
     */
    void setTenantId(Long tenantId);

    /**
     * 获取版本号
     */
    Integer getVersion();

    /**
     * 设置版本号
     */
    void setVersion(Integer version);

    /**
     * 获取删除标记 0:正常;1:已删除
     */
    Boolean getDeleted();

    /**
     * 设置删除标记 0:正常;1:已删除
     */
    void setDeleted(Boolean deleted);

    /**
     * 获取创建人ID
     */
    Long getCreateUserId();

    /**
     * 设置创建人ID
     */
    void setCreateUserId(Long createUserId);

    /**
     * 获取创建人姓名
     */
    String getCreateUserName();

    /**
     * 设置创建人姓名
     */
    void setCreateUserName(String createUserName);

    /**
     * 获取创建时间
     */
    Date getCreateTime();

    /**
     * 设置创建时间
     */
    void setCreateTime(Date createTime);

    /**
     * 获取修改人ID
     */
    Long getUpdateUserId();

    /**
     * 设置修改人ID
     */
    void setUpdateUserId(Long updateUserId);

    /**
     * 获取修改人姓名
     */
    String getUpdateUserName();

    /**
     * 设置修改人姓名
     */
    void setUpdateUserName(String updateUserName);

    /**
     * 获取修改时间
     */
    Date getUpdateTime();

    /**
     * 设置修改时间
     */
    void setUpdateTime(Date updateTime);

}
