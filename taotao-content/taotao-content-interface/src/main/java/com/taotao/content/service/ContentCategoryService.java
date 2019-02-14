package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {

	public List<EasyUITreeNode> getContentCategoryList(Long parentId);

	public TaotaoResult createCategory(TbContentCategory contentCategory);

	public boolean hasContent(long id);

	public TaotaoResult renameCategory(Long id, String name);

	public TaotaoResult removeCategory(Long id);
	
}
