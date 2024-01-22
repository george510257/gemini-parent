package com.gls.gemini.common.core.base;

import java.io.Serializable;
import java.util.List;

public interface ITree<K extends Comparable<K>, T extends ITree<K, T>> extends Serializable {

    K getParentId();

    List<T> getChildren();

    Integer getWeight();
}
