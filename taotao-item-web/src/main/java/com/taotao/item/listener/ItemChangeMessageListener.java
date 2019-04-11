package com.taotao.item.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemChangeMessageListener implements MessageListener{

	@Autowired
	@Qualifier("jedisCluster")
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private ItemService itemService;
	
	@Override
	public void onMessage(Message message) {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		try {
			Template template = cfg.getTemplate("item.ftl");
			if(message instanceof TextMessage) {
					String ids = ((TextMessage) message).getText();
					for (String id : ids.split(",")) {
						System.out.println("删除了商品详情" + id + "的缓存");
						jedisClient.del(ITEM_INFO_KEY + ":" + id + ":BASE");
						jedisClient.del(ITEM_INFO_KEY + ":" + id + ":DESC");
						TbItem item = itemService.getItemById(Long.parseLong(id));
						File file = new File(servletContext.getRealPath("/WEB-INF/freemarker/"+id+".html"));
						if(item != null) {
							Writer writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
							TbItemDesc itemDesc = itemService.getItemDescById(item.getId());
							Map<String, Object> model = new HashMap<>();
							model.put("item", item);
							model.put("itemDesc", itemDesc);
							System.out.println("重新写入了" + id + "的静态页面");
							template.process(model, writer);
						} else {
							System.out.println("删除了" + id + "的静态页面");
							if(file.exists())
								file.delete();
							continue;
						}
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
