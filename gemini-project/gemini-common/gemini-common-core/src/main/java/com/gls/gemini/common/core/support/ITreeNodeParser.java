package com.gls.gemini.common.core.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.gls.gemini.common.core.interfaces.ITreeNode;

public class ITreeNodeParser<Node extends ITreeNode> implements NodeParser<Node, Long> {
    @Override
    public void parse(Node node, Tree<Long> treeNode) {
        treeNode.setId(node.getId());
        treeNode.setParentId(node.getParentId());
        treeNode.setName(node.getName());
        treeNode.setWeight(node.getSort());
        treeNode.putExtra("code", node.getCode());
        treeNode.putExtra("description", node.getDescription());
        treeNode.putExtra("type", node.getType());
        treeNode.putAll(BeanUtil.beanToMap(node));
    }
}
