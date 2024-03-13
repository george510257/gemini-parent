package com.gls.gemini.common.core.interfaces;

/**
 * 树节点
 */
public interface ITreeNode extends IDomain {

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
     * 获取排序
     *
     * @return 排序
     */
    Integer getSort();

}
