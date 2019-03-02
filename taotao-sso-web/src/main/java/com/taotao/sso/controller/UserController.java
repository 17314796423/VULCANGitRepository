package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserRegisterService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@Value("${TAOTAO_TOKEN_KEY}")
	private String TAOTAO_TOKEN_KEY;
	
	@RequestMapping(value="/check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult checkData(@PathVariable("param") String param, @PathVariable("type") Integer type) {
		return userRegisterService.checkData(param, type);
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		return userRegisterService.register(user);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		TaotaoResult result = userRegisterService.login(username,password);
		if(result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, TAOTAO_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
}
