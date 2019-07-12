package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	// 关联查询三张表的数据到索引库中
	// 查询所有的数据
	public List<SearchItem> getsearchItemList();
}
