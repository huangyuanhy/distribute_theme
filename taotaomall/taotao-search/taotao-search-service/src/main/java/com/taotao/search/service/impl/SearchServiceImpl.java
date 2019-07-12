package com.taotao.search.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TAotaoresult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchItemMapper mapper;

	@Autowired
	private SolrServer solrserver;// xml文件已经注入

	@Autowired
	private SearchDao searchDao;

	@Override
	public TAotaoresult importAllSearchContent() throws Exception {
		// 1.注入mapper
		// 2.调用mapper的方法 查询所有的商品的数据
		System.out.println("开始从数据库中查询数据");
		List<SearchItem> searchItemList = mapper.getsearchItemList();
		// 3.通过solrj 将数据写入到索引库中
		// 3.1创建httpsolrserver
		// 3.2 创建solrinputdocument 将 列表中的元素一个个放到索引库中
		for (SearchItem searchItem : searchItemList) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId().toString());// 这里是字符串需要转换
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			// 添加到索引库
			solrserver.add(document);
		}
		// 提交
		solrserver.commit();
		return TAotaoresult.ok();
	}

	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
		// 1.创建solrquery对象
		SolrQuery query = new SolrQuery();
		// 2.设置主查询条件
		if (StringUtils.isNotBlank(queryString)) {
			query.setQuery(queryString);
		} else {
			query.setQuery("*:*");
		}
		// 2.1设置过滤条件 设置分页
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 60;
		query.setStart((page - 1) * rows);// page-1 * rows
		query.setRows(rows);
		// 2.2.设置默认的搜索域
		query.set("df", "item_keywords");
		// 2.3设置高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");
		// 设置高亮显示的域

		// 3.调用dao的方法 返回的是SearchResult 只包含了总记录数和商品的列表
		SearchResult search = searchDao.search(query);

		// 4.设置SearchResult 的总页数
		long pageCount = 0l;
		pageCount = search.getRecordCount() / rows;
		if (search.getRecordCount() % rows > 0) {
			pageCount++;
		}
		search.setPageCount(pageCount); // 5.返回
		return search;
	}

}
