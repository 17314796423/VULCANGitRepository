package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.service.ItemService;
import com.taotao.sso.service.UserLoginService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserLoginService userLoginService;

	@RequestMapping("/add/{itemId}")
	public String addCart(HttpServletRequest request, @PathVariable("itemId") Long itemId, Integer num) throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				cartService.addItemCart(user.getId(), itemService.getItemById(itemId), num);
				return "cartSuccess";
			}
		}
		//未登录
		return "cartSuccess";
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
		//未登录
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartNum(HttpServletRequest request,@PathVariable("itemId") Long itemId,@PathVariable("num") Integer num) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return cartService.updateCartItemByItemId(user.getId(),itemId,num);
			}
		}
		//未登录
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(HttpServletRequest request,@PathVariable("itemId") Long itemId) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				cartService.deleteByItemId(user.getId(), itemId);
				return "redirect:/cart/cart.html";
			}
		}
		//未登录
		return "redirect:/cart/cart.html";
	}
	
}
