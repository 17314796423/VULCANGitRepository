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
	public TaotaoResult saveItem(TbItem item, String desc, String itemParams) {
		return itemService.saveItem(item, desc, itemParams);
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId) {
		return itemService.updateItem(item, desc, itemParams, itemParamId);
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public TaotaoResult deleteItem(String ids) {
		return itemService.changeItemStatus(ids, (byte)3);
	}
	
	@RequestMapping(value="/instock")
	@ResponseBody
	public TaotaoResult instockItem(String ids) {
		return itemService.changeItemStatus(ids, (byte)2);
	}
	
	@RequestMapping(value="/reshelf")
	@ResponseBody
	public TaotaoResult reshelfItem(String ids) {
		return itemService.changeItemStatus(ids, (byte)1);
	}
}
