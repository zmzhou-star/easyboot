package com.github.zmzhou.easyboot.framework.quartz;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.github.zmzhou.easyboot.api.miniapp.lottery.service.LotteryService;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.FileUtil;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务入口类
 * EasyScheduler
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/7 21:29
 */
@Slf4j
@Component
@EnableScheduling
public class EasyScheduler {
	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private LotteryService lotteryService;

	/**
	 * 文件下载保存路径
	 */
	@Value("${server.file-path-download}")
	private String downloadPath;

	/**
	 * heartbeat
	 * @author zmzhou
	 * @date 2020/8/30 12:52
	 */
	public void heartbeat() {
		//	@Scheduled(cron = "0/30 * * * * ?")
		log.info("心跳:{}", LocalDateTime.now());
	}

	/**
	 * 定时查询不在线用户，修改用户状态
	 * @author zmzhou
	 * @date 2020/9/13 12:37
	 */
	public void updateOfflineUserStatus() {
		//	@Scheduled(cron = "0 0/2 * * * ?")
		log.info("定时查询不在线用户，修改用户状态开始...");
		Set<Long> onlineIds = new HashSet<>();
		// 获取所有登录用户的tokenId集合
		Collection<String> keys = redisUtils.keys(Constants.LOGIN_TOKEN_KEY);
		if (!keys.isEmpty()) {
			keys.forEach(key -> {
				// 从redis缓存获取用户登录信息
				LoginUser user = redisUtils.get(key);
				// 保存用户id
				onlineIds.add(user.getUser().getId());
			});
		}
		int record = userService.updateOffline(onlineIds);
		log.info("在线用户id：{}, 修改了{}条记录", onlineIds, record);
	}

	/**
	 * 删除临时文件
	 * @author zmzhou
	 * @date 2020/9/13 15:04
	 */
	public void deleteTempFile() {
		//	@Scheduled(cron = "0 0/10 * * * ?")
		log.info("删除目录：[{}]下的临时文件开始...", downloadPath);
		FileUtil.deleteFile(downloadPath, false);
	}

	/**
	 * 采集彩票数据
	 *
	 * @author zmzhou
	 * @since 2022/7/17 16:44
	 */
	public void collectLotteryData() {
        log.info("定时采集彩票数据开始...");
        lotteryService.collectDltHistoryData();
    }

	/**
	 * 执行有参定时任务
	 * @param params params
	 * @author zmzhou
	 * @date 2020/12/18 18:57
	 */
	public void taskWithParams(String params) {
		log.info("执行有参定时任务：{}", params);
	}
}
