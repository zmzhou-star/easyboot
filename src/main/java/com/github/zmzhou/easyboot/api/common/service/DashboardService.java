package com.github.zmzhou.easyboot.api.common.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.common.vo.CardVo;
import com.github.zmzhou.easyboot.api.system.service.SysNoticeService;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;

/**
 * 首页监控面板service层
 * @title DashboardService
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/21 10:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DashboardService {
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private UserService userService;
	@Resource
	private SysNoticeService noticeService;
	
	/**
	 * 首页面板卡片统计 
	 * @return CardVo
	 * @author zmzhou
	 * @date 2020/12/21 10:51
	 */
	public CardVo cardStat() {
		// 获取所有登录用户的tokenId集合
		Collection<String> keys = redisUtils.keys(Constants.LOGIN_TOKEN_KEY);
		return CardVo.builder().onlineVisitors(keys.size())
				.totalVisitors(userService.count())
				.messages(noticeService.count(String.valueOf(Constants.INT_THREE)))
				.build();
	}
}
