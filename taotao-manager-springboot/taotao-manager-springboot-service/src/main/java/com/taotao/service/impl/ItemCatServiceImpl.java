package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Transactional(propagation=Propagation.REQUIRED)
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbItemCat.getId());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			nodeList.add(treeNode);
		}
		return nodeList;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public TaotaoResult queryByItemCatId(Long itemCatId) {
		try {
			TbItemCatExample example = new TbItemCatExample();
			example.createCriteria().andIdEqualTo(itemCatId);
			List<TbItemCat> list = itemCatMapper.selectByExample(example);
			if(list != null && list.size() > 0) {
				return TaotaoResult.ok(list.get(0).getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "服务器繁忙，请稍后再试！");
		}
		return TaotaoResult.ok();
	}

}
