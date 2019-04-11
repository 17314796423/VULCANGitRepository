package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult<TbContent> getContentList(Long categoryId, Integer page, Integer rows){
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult createContent(TbContent content) {
		return contentService.createContent(content);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content) {
		return contentService.updateContent(content);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		return contentService.deleteContent(ids);
	}
	
}
