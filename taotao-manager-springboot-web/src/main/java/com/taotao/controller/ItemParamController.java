package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.ItemParamResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/itemParam")
public class ItemParamController {
	
	@Reference(timeout=3000000)
	private ItemParamService itemParamService;

	@RequestMapping("/query/{itemCatId}")
	@ResponseBody
	public TaotaoResult queryByItemCatId(@PathVariable("itemCatId")Long itemCatId) {
		return itemParamService.queryByItemCatId(itemCatId);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult<ItemParamResult> queryList(Integer page, Integer rows) {
		return itemParamService.queryList(page,rows);
	}
	
	@RequestMapping(value="/save/{itemCatId}",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveItemParam(@PathVariable("itemCatId")Long itemCatId, String paramData){
		return itemParamService.saveItemParam(itemCatId,paramData);
	}
	
	@RequestMapping(value="/update/{itemCatId}",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItemParam(@PathVariable("itemCatId")Long itemCatId, String paramData){
		return itemParamService.updateItemParam(itemCatId,paramData);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItemParam(String ids){
		return itemParamService.deleteItemParam(ids);
	}
	
}
