package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	@Autowired
	private TbContentCategoryMapper mapper;
	@Override
	public List<TreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample exam = new TbContentCategoryExample();
		Criteria cri = exam.createCriteria();
		cri.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = mapper.selectByExample(exam);
		ArrayList<TreeNode> res = new ArrayList<>();
		for (TbContentCategory content : list) {
			TreeNode node=new TreeNode(content.getId(), content.getName(),
					content.getIsParent()?"closed":"open");
			res.add(node);
		}
		return res;
	}
	@Override
	public TAotaoresult createContentCategory(long parentId, String name) {
		TbContentCategory category=new TbContentCategory();
		category.setCreated(new Date());
		category.setUpdated(new Date());
		category.setName(name);
		category.setParentId(parentId);
		category.setIsParent(false);//创建的都是叶子节点
		category.setSortOrder(1);
		category.setStatus(1);
		
		mapper.insert(category);
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		if (parent!=null&&parent.getIsParent()==false) {
			parent.setIsParent(true);
			//如果原来是叶子节点，则更新为父节点
			mapper.updateByPrimaryKeySelective(parent);
		}
		//插入后，id自增返回填充到category对象中
		return TAotaoresult.ok(category);
	}
	 

}
