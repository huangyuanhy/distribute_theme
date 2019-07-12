package com.taotao.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.search.service.SearchService;
/**
 * 导入数据到索引中
 * @author pc
 *
 */
@Controller
public class ImpoartAll {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("index/importAll")
	@ResponseBody
	public TAotaoresult importAll() throws Exception {
		System.out.println("开始调用服务层");
		return searchService.importAllSearchContent();
	}
	

}
