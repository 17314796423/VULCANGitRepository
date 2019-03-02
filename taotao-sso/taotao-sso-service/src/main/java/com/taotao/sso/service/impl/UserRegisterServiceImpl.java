package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService{

	@Autowired
	private TbUserMapper mapper;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisClient jedisClient;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
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

	@Override
	public TaotaoResult login(String username, String password) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password))
			return TaotaoResult.build(400, "用户名或密码不能为空");
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbUser> list = mapper.selectByExample(example);
		if(list == null || list.size() == 0 || (!list.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))))
			return TaotaoResult.build(400, "用户名或密码不正确");
		list.get(0).setPassword(null);
		String key = USER_INFO + ":" + UUID.randomUUID().toString();
		jedisClient.set(key, JsonUtils.objectToJson(list.get(0)));
		jedisClient.expire(key, SESSION_EXPIRE);
		return TaotaoResult.ok(key.substring(key.indexOf(":") + 1));
	}

}
