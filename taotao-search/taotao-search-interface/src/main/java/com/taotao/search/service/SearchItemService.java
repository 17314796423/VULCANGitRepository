package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {

	public TaotaoResult importAll2Index() throws Exception;
	
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception;
	
}
