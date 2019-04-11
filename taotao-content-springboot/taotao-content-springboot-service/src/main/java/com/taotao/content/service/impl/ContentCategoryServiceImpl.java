package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;

@Transactional(propagation=Propagation.REQUIRED)
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Autowired TbContentMapper contentMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId).andStatusEqualTo(1);
		example.setOrderByClause("sort_order");
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			node.setText(tbContentCategory.getName());
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public TaotaoResult createCategory(TbContentCategory contentCategory) {
		Long parentId = contentCategory.getParentId();
		
		//如果为该父节点添加子节点，必须保证该父节点不能有内容
		/*TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(parentId);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		if(contentList == null || contentList.size() == 0) {*/
			//父节点没有内容，可以添加子节点
			contentCategory.setCreated(new Date());
			contentCategory.setUpdated(contentCategory.getCreated());
			contentCategory.setIsParent(false);
			contentCategory.setSortOrder(1);//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
			contentCategory.setStatus(1);//状态。可选值:1(正常),2(删除)
			contentCategoryMapper.insertSelective(contentCategory);
			
			//如果父节点是叶子结点，则用于添加子节点后将父节点的isparent属性改为true
			TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
			if(!parentCategory.getIsParent()) {
				parentCategory.setIsParent(true);
				contentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
			}
			return TaotaoResult.ok(contentCategory);
		/*} else {
			return TaotaoResult.build(500, "该分类已存在内容，请先删除该分类对应的内容！");
		}*/
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public boolean hasContent(long id) {
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		return (contentList == null || contentList.size() == 0);
	}

	@Override
	public TaotaoResult renameCategory(Long id, String name) {
		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		record.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult removeCategory(Long id) {
		if(id == 30)
			return TaotaoResult.build(500, "删除失败！！该内容分类不可被删除！");
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = category.getParentId();
		if(!category.getIsParent()) {
			
			//先删除该内容分类的内容
			TbContentExample example = new TbContentExample();
			example.createCriteria().andCategoryIdEqualTo(id);
			List<TbContent> contentList = contentMapper.selectByExample(example);
			if(!(contentList == null || contentList.size() == 0)) {
				for (TbContent content : contentList) {
					contentMapper.deleteByPrimaryKey(content.getId());
				}
			}
			
			contentCategoryMapper.deleteByPrimaryKey(id);
			
			//查询父节点是否还有其他子节点，如果没有，则修改isparent为false
			TbContentCategoryExample example1 = new TbContentCategoryExample();
			example1.createCriteria().andParentIdEqualTo(parentId).andIdNotEqualTo(id);
			if(contentCategoryMapper.selectByExample(example1).size() == 0) {
				TbContentCategory record = new TbContentCategory();
				record.setId(parentId);
				record.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKeySelective(record);
			}
			
			return TaotaoResult.ok();
		} else {
			return TaotaoResult.build(500, "删除失败！！请先删除该内容分类的子节点内容分类！");
		}
	}
	
}
