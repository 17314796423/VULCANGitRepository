package com.taotao.item.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/{id}")
	public String getItem(HttpServletRequest request, ModelMap model, @PathVariable("id") Long id) throws Exception {
		if(!new File(request.getSession().getServletContext().getRealPath("/WEB-INF/freemarker/"+id+".html")).exists()) {
			TbItem item = itemService.getItemById(id);
			if(item == null)
				return "notExist";
			TbItemDesc itemDesc = itemService.getItemDescById(id);
			model.addAttribute("item", item);
			model.addAttribute("itemDesc", itemDesc);
			genItemHtml(model, request);
		}
		return id + "";
	}
	
	public void genItemHtml(Map<String, Object> model, HttpServletRequest request) throws Exception {
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template template = cfg.getTemplate("item.ftl");
		Long id = ((TbItem)model.get("item")).getId();
		System.out.println(request.getSession().getServletContext().getRealPath("/WEB-INF/freemarker/"+id+".html"));
		Writer writer = new OutputStreamWriter(new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/freemarker/"+id+".html"))),"utf-8");
		template.process(model, writer);
		writer.close();
	}
	
}
