package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.content.service.ContentCategoryService;
/**
 * 内容分类处理
 * @author pc
 *
 */
@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> getCategoryList(@RequestParam(value="id",defaultValue="0") 
	long parentId){
		return contentCategoryService.getContentCategoryList(parentId);
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TAotaoresult getCategoryList(String name, 
	long parentId){
		return contentCategoryService.createContentCategory(parentId, name);
	}
	
}
