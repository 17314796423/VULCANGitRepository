package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Reference(timeout=3000000)
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
		TaotaoResult result = itemService.saveItem(item, desc, itemParams);
		itemService.sendChangeItemMessage(result.getData() + "");
		return result;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId) {
		TaotaoResult result = itemService.updateItem(item, desc, itemParams, itemParamId);
		itemService.sendChangeItemMessage(item.getId() + "");
		return result;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public TaotaoResult deleteItem(String ids) {
		TaotaoResult result = itemService.changeItemStatus(ids, (byte)3);
		itemService.sendChangeItemMessage(ids);
		return result;
	}
	
	@RequestMapping(value="/instock")
	@ResponseBody
	public TaotaoResult instockItem(String ids) {
		TaotaoResult result = itemService.changeItemStatus(ids, (byte)2);
		itemService.sendChangeItemMessage(ids);
		return result;
	}
	
	@RequestMapping(value="/reshelf")
	@ResponseBody
	public TaotaoResult reshelfItem(String ids) {
		TaotaoResult result = itemService.changeItemStatus(ids, (byte)1);
		itemService.sendChangeItemMessage(ids);
		return result;
	}
}
