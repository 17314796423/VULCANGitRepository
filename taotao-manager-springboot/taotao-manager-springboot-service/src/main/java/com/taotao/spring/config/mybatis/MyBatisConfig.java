package com.taotao.spring.config.mybatis;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.taotao.mapper")
public class MyBatisConfig {
	
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		Resource configLocation = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/SqlMapConfig.xml");
		sqlSessionFactoryBean.setConfigLocation(configLocation);
		return sqlSessionFactoryBean;
	}
	
}
