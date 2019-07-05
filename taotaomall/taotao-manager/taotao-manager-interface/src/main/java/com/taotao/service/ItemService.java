package com.taotao.service;
/**
 * 商品相关的处理的service
 * @author pc
 *
 */

import com.taotao.common.pojo.EasyUIDataGridResult;

public interface ItemService {
	/**
	 * 根据参数分页查询
	 * @return
	 */
public EasyUIDataGridResult getItemList(Integer page,Integer rows);
}
