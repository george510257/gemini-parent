package com.gls.gemini.common.bean.interfaces;

import java.io.Serializable;

/**
 * 树节点
 */
public interface ITreeNode extends Serializable {
    /**
     * 获取id
     *
     * @return id
     */
    Long getId();

    /**
     * 获取编码
     *
     * @return 编码
     */
    String getCode();

    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 获取类型
     *
     * @return 类型
     */
    String getType();

    /**
     * 获取父节点id
     *
     * @return 父节点id
     */
    Long getParentId();

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();

}
