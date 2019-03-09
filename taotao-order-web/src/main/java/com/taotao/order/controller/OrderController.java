package com.taotao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Value("${TAOTAO_TOKEN_KEY}")
	private String TAOTAO_TOKEN_KEY;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("USER_INFO");
		List<TbItem> cartList = cartService.queryCartListByUserId(user.getId());
		if(cartList.size() == 0)
			return "redirect:http://cart.taotao.com/cart/cart.html";
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(OrderInfo info, HttpServletRequest request, Model model) {
		TbUser user = (TbUser) request.getAttribute("USER_INFO");
		info.setBuyerNick(user.getUsername());
		info.setUserId(user.getId());
		TaotaoResult result = orderService.createOrder(info);
		if(result.getStatus() == 200)
			cartService.deleteByUserId(user.getId());
		model.addAttribute("orderId", result.getData());
		model.addAttribute("payment", info.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}
	
}
