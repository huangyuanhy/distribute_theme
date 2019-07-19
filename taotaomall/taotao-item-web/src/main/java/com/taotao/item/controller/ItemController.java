package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService service;
	/**
	 * 获取商品详情
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String getItemDetails(@PathVariable long itemId,Model model) {
		TbItem tbitem = service.getItemById(itemId);
		//类型转换成页面需要的数据类型pojo
		Item item = new Item(tbitem);
		TbItemDesc itemDesc = service.getItemDescById(itemId);
		//将数据填充到jsp页面中
		model.addAttribute("itemDesc", itemDesc);
		model.addAttribute("item", item);
		return "item";
	}
}
