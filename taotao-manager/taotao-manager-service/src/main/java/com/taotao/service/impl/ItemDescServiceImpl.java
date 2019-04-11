package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TaotaoResult queryByItemId(Long itemId) {
		try {
			TbItemDescExample example = new TbItemDescExample();
			example.createCriteria().andItemIdEqualTo(itemId);
			List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
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
