package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Reference(timeout=3000000)
	private SearchItemService searchItemService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public TaotaoResult importAll() throws Exception {
		return searchItemService.importAll2Index();
	}
	
}
