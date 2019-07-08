package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.TreeNode;

public interface ItemCatService {
	/**
	 * 根据父id 查询商品的子类
	 */
	List<TreeNode> getItemCatList(Long parentId);
}
