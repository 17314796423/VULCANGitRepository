package com.taotao.cart.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface CartService {
	
	/**根据商品的ID查询商品的信息
	 * @param userId 哪一个用户的购物车
	 * @param itemId 哪一个商品
	 * @param num  购买商品的数量
	 * @return
	 */
	public TaotaoResult addItemCart(Long userId,TbItem item ,Integer num);
	
	/**
	 * 根据用户ID和商品的ID查询是否存储在redis中
	 * @param userId
	 * @param itemId
	 * @return  null 说明不存在，如果不为空说明存在
	 */
	public TbItem queryTbItemByUserIdAndItemId(Long userId,Long itemId);
	
	public List<TbItem> queryCartListByUserId(Long userId);

	public TaotaoResult updateCartItemByItemId(Long userId, Long itemId, Integer num);

	public TaotaoResult deleteByItemId(Long userId, Long itemId);
	
	public TaotaoResult mergeCookieCart(List<TbItem> cookieCart, Long userId);
	
}
