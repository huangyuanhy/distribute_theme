package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//保存新增内容
		TAotaoresult saveContent(TbContent content);
		//根据分类id 展示相应的内容
		List<TbContent> contentList(long categoryId);
		//List<TbContent> getContentListByCatId(long categoryId);
		
}
