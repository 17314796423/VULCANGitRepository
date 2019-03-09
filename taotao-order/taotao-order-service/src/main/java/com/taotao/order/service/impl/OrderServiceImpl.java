package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.jedis.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	@Qualifier("jedisCluster")
	private JedisClient jedisClient;
	
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Override
	public TaotaoResult createOrder(OrderInfo info) {
		if(!jedisClient.exists(ORDER_GEN_KEY))
			jedisClient.set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
		String orderId = jedisClient.incr(ORDER_GEN_KEY) + "";
		info.setOrderId(orderId);
		info.setCreateTime(new Date());
		info.setUpdateTime(info.getCreateTime());
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		info.setStatus(1);
		orderMapper.insert(info);
		List<TbOrderItem> orderItems = info.getOrderItems();
		for (TbOrderItem orderItem : orderItems) {
			orderItem.setId(jedisClient.incr(ORDER_ITEM_ID_GEN_KEY) + "");
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		TbOrderShipping orderShipping = info.getOrderShipping();
		orderShipping.setCreated(info.getCreateTime());
		orderShipping.setUpdated(info.getCreateTime());
		orderShipping.setOrderId(orderId);
		orderShippingMapper.insert(orderShipping);
		return TaotaoResult.ok(orderId);
	}
	
}
