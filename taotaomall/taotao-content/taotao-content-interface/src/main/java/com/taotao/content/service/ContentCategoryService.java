package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.pojo.TbContent;

public interface ContentCategoryService {
//通过父id查询子id列表
	List<TreeNode> getContentCategoryList(long parentId);
	//添加内容分类
	TAotaoresult createContentCategory(long parentId,String name);
	
}
