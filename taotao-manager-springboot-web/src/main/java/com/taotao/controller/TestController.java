package com.taotao.controller;

import com.taotao.service.TestService;

//@Controller
public class TestController {

	//@Autowired
	private TestService testService;
	
	//@RequestMapping("/test/queryNow")
	//@ResponseBody
	public String queryNow() {
		return testService.queryNow();
	}
}
