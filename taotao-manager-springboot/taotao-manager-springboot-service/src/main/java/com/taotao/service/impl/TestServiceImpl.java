package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;

@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
//@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestMapper testMapper;
	
	@Override
	public String queryNow() {
		return testMapper.queryNow();
	}

}
