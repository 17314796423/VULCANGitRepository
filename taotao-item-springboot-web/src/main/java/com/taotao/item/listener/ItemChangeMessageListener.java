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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.item.jedis.JedisClient;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("itemChangeMessageListener")
@Scope("prototype")
public class ItemChangeMessageListener implements MessageListener{

	@Autowired
	@Qualifier("jedisClientCluster")
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	
	@Value("${spring.freemarker.template-loader-path}")
	private String templateLoaderPath;
	
	@Value("${spring.freemarker.suffix}")
	private String suffix;
	
	@Value("${templateName}")
	private String templateName;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Reference
	private ItemService itemService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void onMessage(Message message) {
		System.out.println(servletContext);
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		try {
			Template template = cfg.getTemplate(templateName);
			if(message instanceof TextMessage) {
					String ids = ((TextMessage) message).getText();
					for (String id : ids.split(",")) {
						System.out.println("删除了商品详情" + id + "的缓存");
						jedisClient.del(ITEM_INFO_KEY + ":" + id + ":BASE");
						jedisClient.del(ITEM_INFO_KEY + ":" + id + ":DESC");
						TbItem item = itemService.getItemById(Long.parseLong(id));
						File file = new File(servletContext.getRealPath(templateLoaderPath + id + suffix));
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
