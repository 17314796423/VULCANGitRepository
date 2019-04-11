package com.taotao.search.listener;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.dao.SearchItemMapper;

public class ItemChangeMessageListener implements MessageListener{

	@Autowired
	private SearchItemMapper mapper;
	
	@Autowired
	@Qualifier("solrCluster")
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			try {
				String ids = ((TextMessage) message).getText();
				String[] split = ids.split(",");
				List<Long> idsl = new ArrayList<>();
				for (String id : split)
					idsl.add(Long.parseLong(id));
				List<SearchItem> list = mapper.getSearchItemListByIds(idsl);
				if(list != null && list.size() > 0) {
					if(list.get(0).getStatus() == 1) {
						List<SolrInputDocument> docs = new ArrayList<>();
						SolrInputDocument doc = null;
						for (SearchItem item : list) {
							doc = new SolrInputDocument();
							doc.addField("id", item.getId());
							doc.addField("item_title", item.getTitle());
							doc.addField("item_sell_point", item.getSell_point());
							doc.addField("item_price", item.getPrice());
							doc.addField("item_image", item.getImage());
							doc.addField("item_category_name", item.getCategory_name());
							doc.addField("item_desc", item.getItem_desc());
							docs.add(doc);
							System.out.println("更新了索引库的" + item.getId());
						}
						solrServer.add(docs);
						solrServer.commit();
					} else {
						List<String> idsd = new ArrayList<>();
						for (String id : split) {
							idsd.add(id);
							System.out.println("删除了索引库中的" + id);
						}
						solrServer.deleteById(idsd);
						solrServer.commit();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
