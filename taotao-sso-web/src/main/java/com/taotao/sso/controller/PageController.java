package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
	
	@RequestMapping("/{page}")
	public String page(@PathVariable("page")String page, String redirect, Model model) {
		if(StringUtils.isNotBlank(redirect))
			model.addAttribute("redirect", redirect);
		return page;
	}
	
}
