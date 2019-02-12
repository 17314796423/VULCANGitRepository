package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemDescService;

@Controller
@RequestMapping("/itemDesc")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/query/{itemId}")
	@ResponseBody
	public TaotaoResult queryByItemId(@PathVariable("itemId")Long itemId) {
		return itemDescService.queryByItemId(itemId);
	}
	
}
