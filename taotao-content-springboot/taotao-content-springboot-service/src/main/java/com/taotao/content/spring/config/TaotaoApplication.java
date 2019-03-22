package com.taotao.content.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication(exclude={QuartzAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound=true, value={"classpath:properties/db.properties","classpath:properties/resource.properties"})
@ComponentScan("com.taotao.content")
@EnableDubbo(scanBasePackages={"com.taotao.content.service.impl"})
public class TaotaoApplication extends SpringBootServletInitializer{
	
	@Value("${jdbc.url}")
	private String jdbcUrl;

	@Value("${jdbc.driver}")
	private String jdbcDriver;

	@Value("${jdbc.username}")
	private String jdbcUsername;

	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	@Bean(destroyMethod="close")
	@Scope("prototype")
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(5);
		return dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaotaoApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TaotaoApplication.class);
	}
	
}
