package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.ItemParamResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	
	public TaotaoResult queryByItemCatId(Long itemCatId);

	public EasyUIDataGridResult<ItemParamResult> queryList(Integer page, Integer rows);

	public TaotaoResult saveItemParam(Long itemCatId, String paramData);

	public TaotaoResult updateItemParam(Long itemCatId, String paramData);

	public TaotaoResult deleteItemParam(String ids);
	
}
