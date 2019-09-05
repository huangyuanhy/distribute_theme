package com.taotao.order.service;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.order.pojo.OrderInfo;

public interface OrderService {
	/**
	 * 创建订单
	 * @param info
	 * @return
	 */
	public TAotaoresult createOrder(OrderInfo info);
}
