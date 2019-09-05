package com.taotao.cart.service;

import java.util.List;

import com.taotao.common.pojo.*;
import com.taotao.pojo.TbItem;

public interface CartService {
	/**
	 * 添加购物车
	 * @param itemId
	 * @param num
	 * @param userId
	 * @return
	 */
	public TAotaoresult addItemCart(TbItem item,Integer num,Long userId);
	/**
	 * 根据用户的ID查询用户的购物车的列表
	 * @param userId
	 * @return
	 */
	public List<TbItem> getCartList(Long userId);
	/**
	 * 根据商品的ID 更新数量
	 * @param itemId 商品的ID
	 * @param num 更新后的数量
	 * @param userId 用户的id  购物车的id
	 * @return
	 */
	public TAotaoresult updateItemCartByItemId(Long userId,Long itemId,Integer num);
	
	
	public TAotaoresult deleteItemCartByItemId(Long userId,Long itemId);
}
