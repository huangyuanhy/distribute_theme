package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * @author pc
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemService;
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	//查阅商品详情
	@ResponseBody
	@RequestMapping(path="/item/list",method=RequestMethod.GET)
	//localhost:8081/item/lsit/getItemList?page=1&rows=30
	public EasyUIDataGridResult getItemList(Integer page,@RequestParam(value="rows") Integer pageSize) {
		return itemService.getItemList(page, pageSize);
	}
}
