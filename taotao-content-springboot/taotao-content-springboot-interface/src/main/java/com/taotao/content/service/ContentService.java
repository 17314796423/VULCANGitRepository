package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	public EasyUIDataGridResult<TbContent> getContentList(Long categoryId, Integer page, Integer rows);

	public TaotaoResult createContent(TbContent content);

	public TaotaoResult updateContent(TbContent content);

	public TaotaoResult deleteContent(String ids);

	public String getContentJsonList(Long categoryId);

}
