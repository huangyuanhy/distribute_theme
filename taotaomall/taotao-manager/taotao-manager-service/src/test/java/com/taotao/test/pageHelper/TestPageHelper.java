package com.taotao.test.pageHelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testHelper() {
		//初始化容器
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//获取mapper代理对象
		TbItemMapper mapper = cxt.getBean(TbItemMapper.class);
		//设置分页信息
		PageHelper.startPage(1, 3);//紧跟着的第一个查询才会被分页
		//调用mapper查询数据
		TbItemExample example = new TbItemExample();
		//此结果被分页  3条数据  res为page对象
		List<TbItem> res = mapper.selectByExample(example);//select * from
		System.out.println("第一次查询结果大小："+res.size());
		//此结果不被分页
		List<TbItem> res1 = mapper.selectByExample(example);//select * from
		System.out.println("第二次查询结果大小："+res1.size());
		
		
		//获取分页信息 所有数据
		PageInfo<TbItem> resInfo=new PageInfo<>(res);
		System.out.println("第一次查询结果大小："+res.size());
		System.out.println(resInfo.getTotal());
		//遍历结果集
		
	}
}
