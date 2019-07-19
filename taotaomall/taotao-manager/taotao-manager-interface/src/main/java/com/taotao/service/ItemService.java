package com.taotao.service;
/**
 * 商品相关的处理的service
 * @author pc
 *
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TAotaoresult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	/**
	 * 根据参数分页查询
	 * @return
	 */
	public EasyUIDataGridResult getItemList(Integer page,Integer rows);
	/**
	 * 根据item插入数据库
	 * @param item
	 * @return
	 */
	TAotaoresult createItem(TbItem item,String desc);
	//根据id查询商品
	public TbItem getItemById(long itemId);
	TbItemDesc getItemDescById(Long itemId);
}
