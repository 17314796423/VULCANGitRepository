package com.taotao.cart.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,QuartzAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound=true, value={"classpath:properties/resource.properties"})
@ComponentScan(basePackages={"com.taotao.cart"})
@EnableDubbo(scanBasePackages={"com.taotao.cart.service.impl"})
public class TaotaoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TaotaoApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TaotaoApplication.class);
	}
	
}
