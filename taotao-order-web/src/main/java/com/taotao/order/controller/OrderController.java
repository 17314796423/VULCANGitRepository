package com.taotao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.cart.service.CartService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Value("${TAOTAO_TOKEN_KEY}")
	private String TAOTAO_TOKEN_KEY;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("USER_INFO");
		List<TbItem> cartList = cartService.queryCartListByUserId(user.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
}
