package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.ItemParamResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;

@Transactional(propagation=Propagation.REQUIRED)
@Service
public class ItemParamServiceImpl implements ItemParamService{

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public TaotaoResult queryByItemCatId(Long itemCatId) {
		try {
			TbItemParamExample example = new TbItemParamExample();
			example.createCriteria().andItemCatIdEqualTo(itemCatId);
			List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
			if(list != null && list.size() > 0) {
				return TaotaoResult.ok(list.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "服务器繁忙，请稍后再试！");
		}
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult<ItemParamResult> queryList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		PageInfo<ItemParamResult> info = new PageInfo<>(itemParamMapper.selectListWithBLOBs());
		EasyUIDataGridResult<ItemParamResult> result = new EasyUIDataGridResult<>();
		result.setTotal(info.getTotal());
		result.setRows(new ArrayList<>(info.getList()));
		info = null;
		return result;
	}

	@Override
	public TaotaoResult saveItemParam(Long itemCatId, String paramData) {
		TbItemParam record = new TbItemParam();
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		record.setItemCatId(itemCatId);
		record.setParamData(paramData);
		itemParamMapper.insertSelective(record);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItemParam(Long itemCatId, String paramData) {
		TbItemParam record = new TbItemParam();
		record.setUpdated(new Date());
		record.setParamData(paramData);
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(itemCatId);
		itemParamMapper.updateByExampleSelective(record, example);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemParam(String ids) {
		String[] split = ids.split(",");
		Long[] args = new Long[split.length];
		int i = 0;
		for (String id : split) {
			args[i++]=Long.parseLong(id);
		}
		itemParamMapper.deleteByIds(args);
		return TaotaoResult.ok();
	}

}
