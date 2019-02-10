package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	public EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows);
	public TaotaoResult saveItem(TbItem item,String desc);
}
