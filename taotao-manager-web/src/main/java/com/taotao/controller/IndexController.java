package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public TaotaoResult importAll() throws Exception {
		return searchItemService.importAll2Index();
	}
	
}
