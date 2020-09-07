package com.zmzhou.easyboot.framework.quartz;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	/**
	 * heartbeat
	 * @author zmzhou
	 * @date 2020/8/30 12:52
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	public void heartbeat() {
		log.info("心跳:{}", LocalDateTime.now());
	}
}
