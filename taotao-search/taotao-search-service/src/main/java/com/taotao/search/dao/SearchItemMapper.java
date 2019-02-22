package com.taotao.search.dao;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	public List<SearchItem> getSearchItemList();
	
	public List<SearchItem> getSearchItemListByIds(List<Long> ids);
	
}
