package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.service.ItemService;
import com.taotao.sso.service.UserLoginService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=3000000)
	private CartService cartService;
	
	@Reference(timeout=3000000)
	private ItemService itemService;
	
	@Reference(timeout=3000000)
	private UserLoginService userLoginService;
	
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;

	@Value("${TT_CART_EXPIRE_TIME}")
	private Integer TT_CART_EXPIRE_TIME;

	@RequestMapping("/add/{itemId}")
	public String addCart(HttpServletRequest request, HttpServletResponse response, @PathVariable("itemId") Long itemId, Integer num) throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				cartService.addItemCart(user.getId(), itemService.getItemById(itemId), num);
				return "cartSuccess";
			}
		}
		//鏈櫥褰�
		addCookieCartItem(request,response,itemId,num);
		return "cartSuccess";
	}
	
	private List<TbItem> getCartList(HttpServletRequest request){
		String cartStr = CookieUtils.getCookieValue(request, TT_CART_KEY, true);
		if(StringUtils.isNoneBlank(cartStr)) {
			List<TbItem> cartList = JsonUtils.jsonToList(cartStr, TbItem.class);
			return cartList;
		}
		return new ArrayList<TbItem>();
	}
	
	private void addCookieCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) throws Exception {
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId() == itemId.longValue()) {
				System.out.println("add" + itemId);
				tbItem.setNum(num);
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), TT_CART_EXPIRE_TIME, true);
				return;
			}
		}
		//璧板嚭鏉ヤ唬琛ㄦ病鏈夊尮閰嶇殑鍟嗗搧
		System.out.println("add new" + itemId);
		TbItem item = itemService.getItemById(itemId);
		item.setImage(item.getImages()!=null?item.getImages()[0]:null);
		item.setNum(num);
		cartList.add(item);
		CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), TT_CART_EXPIRE_TIME, true);
	}

	@RequestMapping("/cart")
	public String cartList(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				List<TbItem> list = cartService.queryCartListByUserId(user.getId());
				request.setAttribute("cartList", list);
				return "cart";
			}
		}
		//鏈櫥褰�
		List<TbItem> cartList = getCartList(request);
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartNum(HttpServletRequest request, HttpServletResponse response,@PathVariable("itemId") Long itemId,@PathVariable("num") Integer num) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return cartService.updateCartItemByItemId(user.getId(),itemId,num);
			}
		}
		//鏈櫥褰�
		updateCookieCartItemNum(request,response,itemId,num);
		return TaotaoResult.ok();
	}
	
	private void updateCookieCartItemNum(HttpServletRequest request, HttpServletResponse response, Long itemId,
			Integer num) {
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId() == itemId.longValue()) {
				System.out.println("update" + itemId);
				tbItem.setNum(num);
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), TT_CART_EXPIRE_TIME, true);
				return;
			}
		}
	}

	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(HttpServletRequest request, HttpServletResponse response,@PathVariable("itemId") Long itemId) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				cartService.deleteByItemId(user.getId(), itemId);
				return "redirect:/cart/cart.html";
			}
		}
		//鏈櫥褰�
		deleteCookieCartItem(request,response,itemId);
		return "redirect:/cart/cart.html";
	}

	private void deleteCookieCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId) {
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId() == itemId.longValue()) {
				System.out.println("delete" + itemId);
				cartList.remove(tbItem);
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), TT_CART_EXPIRE_TIME, true);
				return;
			}
		}
	}
	
}
