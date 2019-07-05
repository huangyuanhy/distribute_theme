package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * @author pc
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	//首页展示
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	//如果参数不一致  @PathVariable(value="page") String pageXXX
	public String showPage(@PathVariable String page) {
		return page;
	}
}
