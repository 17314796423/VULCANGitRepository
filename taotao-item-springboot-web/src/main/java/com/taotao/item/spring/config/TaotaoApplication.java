package com.taotao.item.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication(exclude={QuartzAutoConfiguration.class,ActiveMQAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound=true, value={"classpath:resources/resource.properties"})
@ComponentScan("com.taotao.item")
@EnableDubbo(scanBasePackages={"com.taotao.item.controller"})
public class TaotaoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TaotaoApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TaotaoApplication.class);
	}

}
