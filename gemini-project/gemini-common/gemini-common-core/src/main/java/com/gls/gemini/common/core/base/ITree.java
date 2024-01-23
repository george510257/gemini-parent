package com.gls.gemini.common.core.base;

import java.io.Serializable;
import java.util.List;

public interface ITree<T extends ITree<T>> extends Serializable {

    Long getId();

    T setId(Long id);

    String getCode();

    T setCode(String code);

    String getName();

    T setName(String name);

    Long getParentId();

    T setParentId(Long parentId);

    List<T> getChildren();

    T setChildren(List<T> children);

    Integer getWeight();

    T setWeight(Integer weight);
}
