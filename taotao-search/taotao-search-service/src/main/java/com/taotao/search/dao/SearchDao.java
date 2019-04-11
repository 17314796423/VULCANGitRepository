package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	@Qualifier("solrCluster")
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws SolrServerException {
		SearchResult searchResult = new SearchResult();
		List<SearchItem> itemList = new ArrayList<>();
		SearchItem item = null;
		
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		
		searchResult.setRecordCount(results.getNumFound());
		
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		for (SolrDocument doc : results) {
			item = new SearchItem();
			item.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			item.setImage(doc.getFieldValue("item_image").toString());
			item.setCategory_name(doc.getFieldValue("item_category_name").toString());
			item.setPrice(Long.parseLong(doc.getFieldValue("item_price").toString()));
			item.setSell_point(doc.getFieldValue("item_sell_point").toString());
			List<String> list = highlighting.get(item.getId().toString()).get("item_title");
			if(list != null && list.size() > 0) {
				item.setTitle(list.get(0));
			} else {
				item.setTitle(doc.getFieldValue("item_title").toString());
			}
			itemList.add(item);
		}
		searchResult.setItemList(itemList);
		
		Integer rows = query.getRows();
		long total = results.getNumFound();
		long pageCount = total%rows == 0?total/rows:total/rows+1;
		searchResult.setPageCount(pageCount);
		
		return searchResult;
	}
	
}
