package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination destination;
	
	@Override
	public EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
		PageInfo<TbItem> info = new PageInfo<>(list);
		EasyUIDataGridResult<TbItem> result = new EasyUIDataGridResult<>();
		result.setTotal(info.getTotal());
		result.setRows(new ArrayList<>(info.getList()));
		info = null;
		return result;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc, String itemParams) {
		if(desc == null)
			desc = "";
		if(itemParams == null)
			itemParams = "";
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		date = null;
		// 7 、创建一个TbItemParamItem对象
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		// 8、补全TbItemParamItem的属性
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParams);
		// 9、向商品参数数据表插入数据
		itemParamItemMapper.insert(tbItemParamItem);
		return TaotaoResult.ok(item.getId());
	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId) {
		if(desc == null)
			desc = "";
		if(itemParams == null)
			itemParams = "";
		Date date = new Date();
		item.setUpdated(date);
		itemMapper.updateByPrimaryKeySelective(item);
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(item.getId());
		if(itemDesc == null) {
			// 4、创建一个TbItemDesc对象
			itemDesc = new TbItemDesc();
			// 5、补全TbItemDesc的属性
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(date);
			itemDesc.setUpdated(date);
			itemDescMapper.insert(itemDesc);
		} else {
			if(itemDesc.getItemDesc() == null) {
				itemDesc.setItemDesc(desc);
				itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
			} else {
				if(!itemDesc.getItemDesc().equals(desc)) {
					itemDesc.setItemDesc(desc);
					itemDesc.setUpdated(date);
					itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
				}
			}
		}
		TbItemParamItem itemParamItem = itemParamItemMapper.selectByPrimaryKey(itemParamId);
		if(itemParamItem == null) {
			// 7 、创建一个TbItemParamItem对象
			itemParamItem = new TbItemParamItem();
			// 8、补全TbItemParamItem的属性
			itemParamItem.setCreated(date);
			itemParamItem.setUpdated(date);
			itemParamItem.setItemId(item.getId());
			itemParamItem.setParamData(itemParams);
			itemParamItemMapper.insert(itemParamItem);
		} else {
			if(itemParamItem.getParamData() == null) {
				itemParamItem.setParamData(itemParams);
				itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
			} else {
				if(!itemParamItem.getParamData().equals(itemParams)) {
					itemParamItem.setParamData(itemParams);
					itemParamItem.setUpdated(date);
					itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
				}
			}
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * //商品状态，1-正常，2-下架，3-删除
	 */
	@Override
	public TaotaoResult changeItemStatus(String ids, Byte status) {
		String[] itemIds = ids.split(",");
		for (String itemId : itemIds) {
			TbItem record = new TbItem();
			record.setId(Long.parseLong(itemId));
			record.setStatus(status);
			itemMapper.updateByPrimaryKeySelective(record);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult sendChangeItemMessage(final String ids) {
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(ids);
			}
			
		});
		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(Long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public TbItemDesc getItemDescById(Long id) {
		return itemDescMapper.selectByPrimaryKey(id);
	}

}
