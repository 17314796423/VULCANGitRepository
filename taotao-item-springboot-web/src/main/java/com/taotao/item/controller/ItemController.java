package com.taotao.item.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Value("${spring.freemarker.template-loader-path}")
	private String templateLoaderPath;
	
	@Value("${spring.freemarker.suffix}")
	private String suffix;
	
	@Value("${templateName}")
	private String templateName;
	
	@Reference(timeout=3000000)
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/{id}")
	public String getItem(HttpServletRequest request, ModelMap model, @PathVariable("id") Long id) throws Exception {
		File file = new File(request.getSession().getServletContext().getRealPath(templateLoaderPath + id + suffix));
		if(!file.exists()) {
			TbItem item = itemService.getItemById(id);
			if(item == null)
				return "notExist";
			TbItemDesc itemDesc = itemService.getItemDescById(id);
			model.addAttribute("item", item);
			model.addAttribute("itemDesc", itemDesc);
			genItemHtml(model, request, file);
		}
		return id + "";
	}
	
	public void genItemHtml(Map<String, Object> model, HttpServletRequest request, File file) throws Exception {
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template template = cfg.getTemplate(templateName);
		Long id = ((TbItem)model.get("item")).getId();
		System.out.println(request.getSession().getServletContext().getRealPath(templateLoaderPath + id + suffix));
		Writer writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
		template.process(model, writer);
		writer.close();
	}
	
}
