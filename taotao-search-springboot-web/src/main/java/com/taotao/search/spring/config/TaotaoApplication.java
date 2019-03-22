package com.taotao.search.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication(exclude={QuartzAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound=true, value={"classpath:resources/resource.properties"})
@ComponentScan("com.taotao.search")
@EnableDubbo(scanBasePackages={"com.taotao.search.controller"})
public class TaotaoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TaotaoApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TaotaoApplication.class);
	}

}
