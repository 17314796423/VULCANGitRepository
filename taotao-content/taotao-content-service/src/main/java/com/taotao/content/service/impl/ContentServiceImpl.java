package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.common.pojo.Ad1Node;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisClient jedisClient;
	
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
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
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId() + "");
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId() + "");
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		String[] contentIds = ids.split(",");
		for (String id : contentIds) {
			TbContent content = contentMapper.selectByPrimaryKey(Long.parseLong(id));
			jedisClient.hdel(CONTENT_KEY, content.getCategoryId() + "");
			contentMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		return TaotaoResult.ok();
	}

	@Override
	public String getContentJsonList(Long categoryId) {
		
		System.out.println(jedisClient);
		
		String content_ad1 = jedisClient.hget(CONTENT_KEY, categoryId + "");
		
		if(StringUtils.isNotBlank(content_ad1)) {
			System.out.println("CacheHitRatio:1.0");
			return content_ad1;
		}
		
		System.out.println("CacheHitRatio:0.0");
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		List<Ad1Node> nodeList = new ArrayList<>();
		Ad1Node ad1Node = null;
		for (TbContent content : list) {
			ad1Node = new Ad1Node(content.getPic2(), 
										AD1_HEIGHT, 
										content.getSubTitle(), 
										AD1_WIDTH, 
										content.getPic(),
										AD1_WIDTH_B,
										content.getUrl(),
										AD1_HEIGHT_B);
			nodeList.add(ad1Node);
		}
		content_ad1 = JsonUtils.objectToJson(nodeList);
		
		jedisClient.hset(CONTENT_KEY, categoryId + "", content_ad1);
		
		return content_ad1;
	}

}
