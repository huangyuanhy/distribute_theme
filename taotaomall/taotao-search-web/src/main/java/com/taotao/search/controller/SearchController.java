package com.taotao.search.controller;

import java.security.Provider.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 提供搜索服务
 * @author pc
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Value("${ITEM_ROWS}")
	private Integer rows;

	@RequestMapping("/search")//localhost:8085/search.html?q="xxx"
	public String search( @RequestParam(defaultValue="1") Integer page, @RequestParam("q") String queryStr,Model model) throws Exception{
		//解决get乱码
		queryStr = new String(queryStr.getBytes("iso-8859-1"), "utf-8");
		
		SearchResult res = searchService.search(queryStr, page, rows);
		//将数据填充到jsp页面中,key已在页面中定义好
		model.addAttribute("query", queryStr);
		model.addAttribute("totalPages", res.getPageCount());
		model.addAttribute("itemList", res.getItemList());
		model.addAttribute("page", page);
		//返回逻辑视图
		return "search";

	}
}
