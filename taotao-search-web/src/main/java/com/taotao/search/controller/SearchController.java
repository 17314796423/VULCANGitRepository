package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;

@Controller
public class SearchController {

	@Autowired
	private SearchItemService searchItemService;

	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@RequestMapping("/search")
	public String search(Integer page, @RequestParam(value="q")String queryString, Model model) throws Exception {
		queryString = new String(queryString.getBytes("iso-8859-1"), "utf-8");
		SearchResult searchResult = searchItemService.search(queryString, page, ITEM_ROWS);
		if(page == null)
			page = 1;
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		return "search";
	}
	
}
