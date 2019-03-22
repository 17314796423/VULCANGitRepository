package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserRegisterService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserRegisterServiceImpl implements UserRegisterService{

	@Autowired
	private TbUserMapper mapper;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Override
	public TaotaoResult checkData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria c = example.createCriteria();
		List<TbUser> list = null;
		if(type == 1) {
			/*if(StringUtils.isBlank(param))
				return TaotaoResult.ok(false);*/
			c.andUsernameEqualTo(param);
		} else if (type == 2) {
			/*if(StringUtils.isBlank(param))
				return TaotaoResult.ok(true);*/
			c.andPhoneEqualTo(param);
		} else if (type == 3) {
			/*if(StringUtils.isBlank(param))
				return TaotaoResult.ok(true);*/
			c.andEmailEqualTo(param);
		} else {
			return TaotaoResult.build(400, "非法的参数");
		}
		list = mapper.selectByExample(example);
		if(list != null && list.size() > 0)
			return TaotaoResult.ok(false);
		return TaotaoResult.ok(true);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()))
			return TaotaoResult.build(400, "注册失败.请校验数据后请再提交数据");
		if(!(boolean)checkData(user.getUsername(), 1).getData())
			return TaotaoResult.build(400, "用户名已经被注册");
		if(StringUtils.isNotBlank(user.getPhone())) {
			if(!(boolean)checkData(user.getPhone(), 2).getData())
				return TaotaoResult.build(400, "手机已经被注册");
		}
		if(StringUtils.isNotBlank(user.getEmail())) {
			if(!(boolean)checkData(user.getEmail(), 3).getData())
				return TaotaoResult.build(400, "邮箱已经被注册");
		}
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		mapper.insertSelective(user);
		return TaotaoResult.ok();
	}

}
