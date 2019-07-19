package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 从mq中接收消息，查询数据库，生成静态页面
 * @author pc
 *
 */
public class HTMLGenListener implements MessageListener{
	@Autowired
	private ItemService service;
	@Autowired
	private FreeMarkerConfig freemarkerConfig;
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			//获取商品id
			long itemId;
			try {
				System.out.println("item-web 检测到商品id新增或是修改");
				itemId = Long.valueOf(((TextMessage) message).getText());
				//查询数据库
				TbItem base = service.getItemById(itemId);
				Item item=new Item(base);//转成需要的pojo
				TbItemDesc itemDdesc = service.getItemDescById(itemId);
				//生成静态页面
				//genFreemarker(item,itemDdesc);
			 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	private void genFreemarker(Item item, TbItemDesc itemDdesc) {
		Configuration config = freemarkerConfig.getConfiguration();
		try {
			System.out.println("开始生成freemarker模板数据");
			//获取模板文件
			Template template = config.getTemplate("item.ftl");
			//准备数据
			HashMap<Object, Object> map = new HashMap<>();
			map.put("item", item);
			map.put("itemDesc", itemDdesc);
			FileWriter out = new FileWriter(new File("e:\\test.html"));
			template.process(map, out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
