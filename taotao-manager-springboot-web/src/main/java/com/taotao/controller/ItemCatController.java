package com.taotao.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Reference(timeout=3000000)
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		return itemCatService.getItemCatList(parentId);
	}
	
	@RequestMapping("/query/{itemCatId}")
	@ResponseBody
	public TaotaoResult queryByItemCatId(@PathVariable("itemCatId")Long itemCatId) {
		return itemCatService.queryByItemCatId(itemCatId);
	}
}
