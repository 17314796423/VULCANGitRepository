package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public EasyUIDataGridResult<TbContent> getContentList(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);//解决pagehelper和逆向工程兼容问题https://blog.csdn.net/wangpf2011/article/details/75093308
		PageInfo<TbContent> info = new PageInfo<>(list);
		EasyUIDataGridResult<TbContent> result = new EasyUIDataGridResult<>();
		result.setRows(new ArrayList<>(info.getList()));
		result.setTotal(info.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		contentMapper.insertSelective(content);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		String[] contentIds = ids.split(",");
		for (String id : contentIds) {
			contentMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		return TaotaoResult.ok();
	}

}
