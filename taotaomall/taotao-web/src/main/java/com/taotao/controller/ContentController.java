package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/save")
	@ResponseBody
	public TAotaoresult saveContent(TbContent content) {
		return contentService.saveContent(content);
	}
	//method:'get',pageSize:20,url:'/content/query/list',queryParams:{categoryId:0
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public List<TbContent> contentList(@RequestParam(defaultValue="0") long categoryId) {
		return contentService.contentList(categoryId);
	}
	
}
