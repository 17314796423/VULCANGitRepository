package com.taotao.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
=======
>>>>>>> da6b13a6edc5c5c4b6e6ca813e8402ffa7417074
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

<<<<<<< HEAD
@SpringBootApplication(exclude={QuartzAutoConfiguration.class,DataSourceAutoConfiguration.class})
=======
@SpringBootApplication(exclude={QuartzAutoConfiguration.class})
>>>>>>> da6b13a6edc5c5c4b6e6ca813e8402ffa7417074
@PropertySource(ignoreResourceNotFound=true, value={"classpath:resources/resource.properties"})
@ComponentScan("com.taotao")
@EnableDubbo(scanBasePackages={"com.taotao.controller"})
public class TaotaoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TaotaoApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TaotaoApplication.class);
	}
	
}
