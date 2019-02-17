package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchItemMapper;
import com.taotao.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService{
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAll2Index() throws SolrServerException, IOException {
		List<SearchItem> list = searchItemMapper.getSearchItemList();
		List<SolrInputDocument> docs = new ArrayList<>();
		SolrInputDocument doc = null;
		for (SearchItem searchItem : list) {
			doc = new SolrInputDocument();
			doc.addField("id", searchItem.getId());
			doc.addField("item_title", searchItem.getTitle());
			doc.addField("item_sell_point", searchItem.getSell_point());
			doc.addField("item_price", searchItem.getPrice());
			doc.addField("item_image", searchItem.getImage());
			doc.addField("item_category_name", searchItem.getCategory_name());
			doc.addField("item_desc", searchItem.getItem_desc());
			docs.add(doc);
		}
		solrServer.add(docs);
		solrServer.commit();
		System.out.println("ok");
		return TaotaoResult.ok();
	}
	
}
