package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;
import com.taotao.sso.service.UserRegisterService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@Autowired
	private UserLoginService userLoginService;
	
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
		TaotaoResult result = userLoginService.login(username,password);
		if(result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, TAOTAO_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
	@RequestMapping(value="/token/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object getUserByToken(@PathVariable("token") String token, String callback) {
		TaotaoResult result = userLoginService.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)) {
			MappingJacksonValue value = new MappingJacksonValue(result);
			value.setJsonpFunction(callback);
			return value;
		}
		return result;
	}
	
	@RequestMapping(value="/logout/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object logout(@PathVariable("token") String token, String callback) {
		TaotaoResult result = userLoginService.logout(token);
		if(StringUtils.isNotBlank(callback)) {
			MappingJacksonValue value = new MappingJacksonValue(result);
			value.setJsonpFunction(callback);
			return value;
		}
		return result;
	}
	
}
