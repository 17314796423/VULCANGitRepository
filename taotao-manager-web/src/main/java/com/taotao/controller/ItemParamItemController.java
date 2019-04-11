package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamItemService;

@Controller
@RequestMapping("/itemParamItem")
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/query/{itemId}")
	@ResponseBody
	public TaotaoResult queryByItemId(@PathVariable("itemId")Long itemId) {
		return itemParamItemService.queryByItemId(itemId);
	}
}
