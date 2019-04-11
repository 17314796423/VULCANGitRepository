package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.content.service.ContentService;

@Controller
public class IndexController {

	@Reference(timeout=3000000)
	private ContentService contentService;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String content_ad1 = contentService.getContentJsonList(AD1_CATEGORY_ID);
		model.addAttribute("content_ad1", content_ad1);
		return "index";
	}
	
}
