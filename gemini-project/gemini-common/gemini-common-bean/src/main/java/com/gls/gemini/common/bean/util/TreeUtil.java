package com.gls.gemini.common.bean.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import com.gls.gemini.common.bean.interfaces.ITreeNode;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TreeUtil extends cn.hutool.core.lang.tree.TreeUtil {

    public <R extends ITreeNode> List<Tree<Long>> buildTree(List<R> nodeList) {
        return build(nodeList, 0L, (object, treeNode) -> {
            treeNode.setId(object.getId());
            treeNode.setParentId(object.getParentId());
            treeNode.setName(object.getName());
            treeNode.setWeight(object.getWeight());
            treeNode.putExtra("code", object.getCode());
            treeNode.putExtra("description", object.getDescription());
            treeNode.putExtra("type", object.getType());
            treeNode.putAll(BeanUtil.beanToMap(object));
        });
    }
}
