package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows){
		EasyUIDataGridResult<TbItem> result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc) {
		return itemService.saveItem(item, desc);
	}
}
