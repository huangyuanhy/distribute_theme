package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper mapper;
	@Autowired
	private JedisClient client;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	
	@Override
	public TAotaoresult saveContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		mapper.insert(content);
		
		//清空缓存 数据同步处理
		try {
			System.out.println("插入数据的时候，清空缓存");
			client.hdel(CONTENT_KEY, content.getCategoryId()+"");
		} catch (Exception e) {
		}
		
		return TAotaoresult.ok();
	}
	public List<TbContent> contentList(long categoryId) {
		//先从缓存中查找
		String jsonstr = client.hget(CONTENT_KEY, categoryId+"");
		if (!StringUtils.isEmpty(jsonstr)) {
			System.out.println("在缓存中找到数据了");
			return JsonUtils.jsonToList(jsonstr,TbContent.class);
		}
		//缓存没有，查询数据库
		System.out.println("缓存没有，查询数据库");
		TbContentExample exam = new TbContentExample();
		Criteria cri = exam.createCriteria();
		cri.andCategoryIdEqualTo(categoryId);
		List<TbContent> res = mapper.selectByExample(exam);

		//将结果添加到缓存 为避免影响正常业务逻辑，加上异常处理
		try {
			client.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(res));

		} catch (Exception e) {
			System.out.println("添加缓存失败");
		}

		return res;
	}

}
