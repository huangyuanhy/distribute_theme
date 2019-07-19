package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TAotaoresult;
import com.taotao.common.utils.IDutil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper descmapper;
	@Autowired
	private JmsTemplate jmstemplate;
	@Resource(name="topicDestination")
	private Destination destination;
	@Autowired
	private JedisClient client;
	
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
	@Override
	public TAotaoresult createItem(TbItem item,String desc) {
		// 生产商品id
		long itemId = IDutil.getItemId();
		//状态 1 正常      2 下架  3 删除
		item.setId(itemId);
		item.setStatus((byte) 1);//
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insert(item);
		// 3.补全商品描述中的属性
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(itemId);
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		// 4.插入商品描述数据
		// 注入tbitemdesc的mapper
		descmapper.insertSelective(desc2);

		// 添加发送消息的业务逻辑
		jmstemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送的消息
				System.out.println("----新增商品id:"+itemId);
				return session.createTextMessage(itemId+"");
			}
		});
		// 5.返回taotaoresult
		return TAotaoresult.ok();
	}

	@Override
	public TbItem getItemById(long itemId) {
		//查询缓存
		try {
			String value = client.get("ITEM_INFO:"+itemId+":BASE");
			if (StringUtils.isNotBlank(value)) {
				System.out.println("缓存中有数据");
				client.expire("ITEM_INFO:"+itemId+":BASE", 86400);
				return JsonUtils.jsonToPojo(value, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("从数据库中查找数据");
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		
		//添加缓存 以：分割实现分类 
		client.set("ITEM_INFO:"+itemId+":BASE", JsonUtils.objectToJson(tbItem));
		//过期时间一天 只有string类型才能设置过期时间，hash不能争对具体的field设置过期时间
		client.expire("ITEM_INFO:"+itemId+":BASE", 86400);
		//返回tbitem
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		//查询缓存
				try {
					String value = client.get("ITEM_INFO:"+itemId+":BASE");
					if (StringUtils.isNotBlank(value)) {
						System.out.println("缓存中有数据");
						client.expire("ITEM_INFO:"+itemId+":BASE", 86400);
						return JsonUtils.jsonToPojo(value, TbItemDesc.class);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				TbItemDesc desc = descmapper.selectByPrimaryKey(itemId);
				//添加缓存 以：分割实现分类 
				client.set("ITEM_INFO:"+itemId+":BASE", JsonUtils.objectToJson(desc));
				//过期时间一天 只有string类型才能设置过期时间，hash不能争对具体的field设置过期时间
				client.expire("ITEM_INFO:"+itemId+":BASE", 86400);
		return desc;
	}
	
 

}
