package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zmzhou.easyboot.api.monitor.vo.SysUserOnlineVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

/**
 * 在线用户监控
 * SysUserOnlineService
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/12 17:23
 */
@Service
public class SysUserOnlineService {
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 获取在线用户列表
	 *
	 * @param userName 用户名
	 * @param ipAddr   IP地址
	 * @return 在线用户列表
	 * @author zmzhou
	 * @date 2020/9/12 17:36
	 */
	public List<SysUserOnlineVo> findAll(String userName, String ipAddr) {
		List<SysUserOnlineVo> onlineList = new ArrayList<>();
		// 获取所有登录用户的tokenId集合
		Collection<String> keys = redisUtils.keys(Constants.LOGIN_TOKEN_KEY);
		keys.forEach(key -> {
			// 从redis缓存获取用户登录信息
			LoginUser user = redisUtils.get(key);
			// 条件查询
			if (StringUtils.isNotBlank(ipAddr) && StringUtils.isNotBlank(userName)) {
				onlineList.add(selectByInfo(ipAddr, userName, user));
			} else if (StringUtils.isNotBlank(ipAddr)) {
				onlineList.add(selectByIpaddr(ipAddr, user));
			} else if (StringUtils.isNotBlank(userName) && null != (user.getUser())) {
				onlineList.add(selectByUserName(userName, user));
			} else {
				onlineList.add(converter(user));
			}
		});
		// 删除集合中的空对象
		onlineList.removeAll(Collections.singleton(null));
		// 按登陆时间倒序排序
		Collections.sort(onlineList);
		return onlineList;
	}

	/**
	 * 通过登录IP查询
	 * @param ipAddr 登录地址
	 * @param user   用户信息
	 * @return 在线用户信息
	 * @author zmzhou
	 * @date 2020/9/12 17:55
	 */
	public SysUserOnlineVo selectByIpaddr(String ipAddr, LoginUser user) {
		// 模糊查询
		if (null != user.getIpAddr() && user.getIpAddr().contains(ipAddr)) {
			return converter(user);
		}
		return null;
	}

	/**
	 * 通过用户名称查询
	 *
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 * @author zmzhou
	 * @date 2020/9/12 17:55
	 */
	public SysUserOnlineVo selectByUserName(String userName, LoginUser user) {
		// 模糊查询
		if (null != user.getUsername() && user.getUsername().contains(userName)) {
			return converter(user);
		}
		return null;
	}

	/**
	 * 通过登录IP、用户名称查询
	 *
	 * @param ipAddr   登录地址
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 * @author zmzhou
	 * @date 2020/9/12 17:55
	 */
	public SysUserOnlineVo selectByInfo(String ipAddr, String userName, LoginUser user) {
		// 模糊查询
		if (null != user.getIpAddr() && user.getIpAddr().contains(ipAddr)
				&& null != user.getUsername() && user.getUsername().contains(userName)) {
			return converter(user);
		}
		return null;
	}
	/**
	 * 设置在线用户信息
	 * @param user 用户信息
	 * @return 在线用户
	 * @author zmzhou
	 * @date 2020/9/12 17:56
	 */
	public SysUserOnlineVo converter(LoginUser user) {
		if (null == user || null == user.getUser()) {
			return null;
		}
		SysUserOnlineVo userOnlineVo = new SysUserOnlineVo();
		// 实体类转换
		BeanUtils.copyProperties(user, userOnlineVo);
		return userOnlineVo;
	}
}
