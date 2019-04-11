package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	public EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows);
	public TaotaoResult saveItem(TbItem item,String desc, String itemParams);
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId);
	public TaotaoResult changeItemStatus(String ids, Byte status);
	public TaotaoResult sendChangeItemMessage(final String ids);
	public TbItem getItemById(Long id) throws Exception;
	public TbItemDesc getItemDescById(Long id) throws Exception;
}
