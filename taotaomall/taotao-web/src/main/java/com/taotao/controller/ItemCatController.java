package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TreeNode;
import com.taotao.service.ItemCatService;

/**
 * 商品分类管理
 * @author pc
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@ResponseBody
	@RequestMapping("/item/cat/list")
	public List<TreeNode> getItemList(@RequestParam(name="id",defaultValue="0") Long parentId){
		return itemCatService.getItemCatList(parentId);
		
	}
}




