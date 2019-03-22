package com.taotao.search.spring.config.solr;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass({ HttpSolrClient.class, CloudSolrClient.class })
public class SolrSpringConfig {

	private SolrClient solrClient;
	
	@Value("${spring.data.solr.zk-host}")
	private String zkHost;
	
	@Value("${spring.data.solr.host}")
	private String baseUrl;
	
	@Value("${spring.data.solr.defaultCollection}")
	private String defaultCollection;

	@Bean
	@ConditionalOnMissingBean
	public SolrClient solrClient() {
		createSolrClient();
		return this.solrClient;
	}

	@SuppressWarnings("deprecation")
	private SolrClient createSolrClient() {
		if (StringUtils.hasText(zkHost)) {
			solrClient = new CloudSolrClient.Builder().withZkHost(Arrays.asList(zkHost.split(","))).build();
			((CloudSolrClient)solrClient).setDefaultCollection(defaultCollection);
		} else {
			solrClient = new HttpSolrClient.Builder(baseUrl).build();
		}
		System.out.println(solrClient);
		return solrClient;
	}
	
}
