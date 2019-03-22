package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemDescService;

@Controller
@RequestMapping("/itemDesc")
public class ItemDescController {
	
	@Reference(timeout=3000000)
	private ItemDescService itemDescService;
	
	@RequestMapping("/query/{itemId}")
	@ResponseBody
	public TaotaoResult queryByItemId(@PathVariable("itemId")Long itemId) {
		return itemDescService.queryByItemId(itemId);
	}
	
}
