package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemCatService {
	
	public List<EasyUITreeNode> getItemCatList(long parentId);

	public TaotaoResult queryByItemCatId(Long itemCatId);
	
}
