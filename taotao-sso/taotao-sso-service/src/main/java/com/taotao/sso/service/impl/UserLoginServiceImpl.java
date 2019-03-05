package com.taotao.sso.service.impl;

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
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService{

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

	@Override
	public TaotaoResult getUserByToken(String token) {
		String jsonStr = jedisClient.get(USER_INFO + ":" + token);
		if(StringUtils.isNotBlank(jsonStr)) {
			jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
			return TaotaoResult.ok(JsonUtils.jsonToPojo(jsonStr, TbUser.class));//转pojo为了能让web层用responsebody转json返回时候能正确解析data中的数据，而不会当字符串
		}
		return TaotaoResult.build(400, "用户登录已经过期，请重新登录。");
	}

	@Override
	public TaotaoResult logout(String token) {
		if(jedisClient.exists(USER_INFO + ":" + token)) {
			jedisClient.del(USER_INFO + ":" + token);
			return TaotaoResult.ok();
		} else {
			return TaotaoResult.build(400, "该账号不存在或已经过期");
		}
	}

}
