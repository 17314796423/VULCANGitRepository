package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TaotaoResult queryByItemId(Long itemId) {
		try {
			TbItemParamItemExample example = new TbItemParamItemExample();
			example.createCriteria().andItemIdEqualTo(itemId);
			List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
			if(list != null && list.size() > 0) {
				return TaotaoResult.ok(list.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "服务器繁忙，请稍后再试！");
		}
		return TaotaoResult.ok();
	}
	
	
	
}
