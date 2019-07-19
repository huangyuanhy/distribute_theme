package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.pojo.TAotaoresult;
import com.taotao.search.service.SearchService;

public class ItemChangMessageListener implements MessageListener {
	@Autowired
	private SearchService service;

	@Override
	public void onMessage(Message message) {
		// 判断消息是否为textmessage
		if (message instanceof TextMessage) {
			// 如果是获取商品的id
			try {
				Long itemId = Long.valueOf(((TextMessage) message).getText());
				System.out.println("搜索服务发现商品id" + itemId + "新增或是修改，同步更新开始");

				// 更新索引库
				// 通过商品的id查询数据 需要开发mapper 通过id查询商品(搜索时)的数据
				TAotaoresult taotaoResult = service.updateSearchItemById(itemId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

}
