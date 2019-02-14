package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContentCategory;

@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		return contentCategoryService.getContentCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(TbContentCategory contentCategory) {
		return contentCategoryService.createCategory(contentCategory);
	}
	
	@RequestMapping("/hasContent")
	@ResponseBody
	public boolean hasContent(long id) {
		return contentCategoryService.hasContent(id);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult renameCategory(Long id, String name) {
		return contentCategoryService.renameCategory(id, name);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult removeCategory(Long id) {
		return contentCategoryService.removeCategory(id);
	}
	
}
