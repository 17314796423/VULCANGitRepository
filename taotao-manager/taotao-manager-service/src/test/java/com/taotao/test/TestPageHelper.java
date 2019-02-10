package com.taotao.test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testPageHelper() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 5);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		List<TbItem> list2 = itemMapper.selectByExample(example);
		PageInfo<TbItem> info = new PageInfo<>(list);
		System.out.println(info.getTotal());
		System.out.println(list.size());
		for (TbItem tbItem : info.getList()) {
			System.out.println(tbItem.getTitle());
		}
		System.out.println(list2.size());
		applicationContext.getClass().getMethod("close").invoke(applicationContext);
	}
}
