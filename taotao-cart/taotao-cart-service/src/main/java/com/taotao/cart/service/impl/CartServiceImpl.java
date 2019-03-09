package com.taotao.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.cart.jedis.JedisClient;
import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService{
	
	@Value("${TT_CART_REDIS_PRE_KEY}")
	private String TT_CART_REDIS_PRE_KEY;

	@Autowired
	@Qualifier("jedisCluster")
	private JedisClient jedisClient;
	
	@Override
	public TaotaoResult addItemCart(Long userId, TbItem item, Integer num) {
		TbItem tbItem = queryTbItemByUserIdAndItemId(userId, item.getId());
		if(tbItem != null) {
			tbItem.setNum(tbItem.getNum() + num);
			jedisClient.hset(TT_CART_REDIS_PRE_KEY + ":" + userId, item.getId() + "", JsonUtils.objectToJson(tbItem));
		} else {
			item.setImage(item.getImages()[0]);
			item.setNum(num);
			jedisClient.hset(TT_CART_REDIS_PRE_KEY + ":" + userId, item.getId() + "", JsonUtils.objectToJson(item));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TbItem queryTbItemByUserIdAndItemId(Long userId, Long itemId) {
		String jsonStr = jedisClient.hget(TT_CART_REDIS_PRE_KEY + ":" + userId, itemId + "");
		if(StringUtils.isNotBlank(jsonStr))
			return JsonUtils.jsonToPojo(jsonStr, TbItem.class);
		return null;
	}

	@Override
	public List<TbItem> queryCartListByUserId(Long userId) {
		List<TbItem> cartList = new ArrayList<>();
		Map<String, String> cart = jedisClient.hgetAll(TT_CART_REDIS_PRE_KEY + ":" + userId);
		for (Entry<String, String> entry : cart.entrySet()) {
			String jsonStr = entry.getValue();
			if(StringUtils.isNoneBlank(jsonStr)) {
				TbItem item = JsonUtils.jsonToPojo(jsonStr, TbItem.class);
				cartList.add(item);
			}
		}
		return cartList;
	}

	@Override
	public TaotaoResult updateCartItemByItemId(Long userId, Long itemId, Integer num) {
		TbItem item = queryTbItemByUserIdAndItemId(userId, itemId);
		if(item != null) {
			item.setNum(num);
			jedisClient.hset(TT_CART_REDIS_PRE_KEY + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteByItemId(Long userId, Long itemId) {
		jedisClient.hdel(TT_CART_REDIS_PRE_KEY + ":" + userId, itemId + "");
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult mergeCookieCart(List<TbItem> cookieCart, Long userId) {
		List<TbItem> cartList = queryCartListByUserId(userId);
		for (TbItem cookieItem : cookieCart) {
			Boolean flag = false;
			for (TbItem tbItem : cartList) {
				if(cookieItem.getId() == tbItem.getId().longValue()) {
					System.out.println("merge" + tbItem.getId());
					tbItem.setNum(tbItem.getNum() + cookieItem.getNum());
					jedisClient.hset(TT_CART_REDIS_PRE_KEY + ":" + userId, tbItem.getId() + "", JsonUtils.objectToJson(tbItem));
					flag = true;
					break;
				}
			}
			if(flag)
				continue;
			System.out.println("merge new" + cookieItem.getId());
			jedisClient.hset(TT_CART_REDIS_PRE_KEY + ":" + userId, cookieItem.getId() + "", JsonUtils.objectToJson(cookieItem));
		}
			
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteByUserId(Long userId) {
		jedisClient.del(TT_CART_REDIS_PRE_KEY + ":" + userId);
		return TaotaoResult.ok();
	}

}
