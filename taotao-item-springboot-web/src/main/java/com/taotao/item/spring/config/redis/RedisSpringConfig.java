package com.taotao.item.spring.config.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisSpringConfig {
	
	@Value("${redis.host}")
	private String host;
	
	@Value("${redis.port}")
	private Integer port;
	
	@Value("${redis.cluster}")
	private String cluster;
	
	@Bean
	@ConditionalOnMissingBean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//最大连接数
		jedisPoolConfig.setMaxTotal(30);
		//最大空闲连接数
		jedisPoolConfig.setMaxIdle(10);
		//每次释放连接的最大数目 
		jedisPoolConfig.setNumTestsPerEvictionRun(1024);
		//释放连接的扫描间隔（毫秒
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
		//连接最小空闲时间
		jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);
		//连接空闲多久后释放, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放
		jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(10000);
		//获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1
		jedisPoolConfig.setMaxWaitMillis(1500);
		//在获取连接的时候检查有效性, 默认false
		jedisPoolConfig.setTestOnBorrow(false);
		//在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(true);
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		jedisPoolConfig.setBlockWhenExhausted(false);
		return jedisPoolConfig;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public JedisPool JedisPool(JedisPoolConfig jedisPoolConfig){
		return new JedisPool(jedisPoolConfig, host, port);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public JedisCluster jedisCluster(JedisPoolConfig jedisPoolConfig){
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		for (String hostAndPort : cluster.split(",")) {
			String[] arr = hostAndPort.split(":");
			nodes.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
		}
		return new JedisCluster(nodes, jedisPoolConfig);
	}
	
}
