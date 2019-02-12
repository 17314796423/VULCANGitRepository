package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/itemParam")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/{itemCatId}")
	@ResponseBody
	public TaotaoResult queryByItemCatId(@PathVariable("itemCatId")Long itemCatId) {
		return itemParamService.queryByItemCatId(itemCatId);
	}
	
}
