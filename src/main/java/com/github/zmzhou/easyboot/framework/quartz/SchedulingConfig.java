package com.github.zmzhou.easyboot.framework.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 执行定时任务的线程池配置类
 *
 * @author zmzhou
 * @version 1.0
 * @title SchedulingConfig
 * @date 2020/12/15 21:24
 */
@Configuration
public class SchedulingConfig {
	/**
	 * 
	 * @author zmzhou
	 * @date 2020/12/15 21:24
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		// 定时任务执行线程池核心线程数
		taskScheduler.setPoolSize(4);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setThreadNamePrefix("Easy-boot-TaskSchedulerThreadPool-");
		return taskScheduler;
	}
}