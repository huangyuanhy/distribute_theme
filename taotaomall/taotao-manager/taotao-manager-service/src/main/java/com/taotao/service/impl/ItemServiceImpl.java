package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		//查询  结果实际是Page对象
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		//获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		//封装信息
		EasyUIDataGridResult res = new EasyUIDataGridResult();
		res.setRows(pageInfo.getList());
		res.setTotal((int) pageInfo.getTotal());
		return res;
	}

}
