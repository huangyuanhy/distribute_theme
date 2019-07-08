package com.taotao.poratal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
/**
 * 前台首页展示
 */
	@RequestMapping("/index")
	public String showIndex() {
		//返回一个逻辑视图
		return "index";
	}
}
