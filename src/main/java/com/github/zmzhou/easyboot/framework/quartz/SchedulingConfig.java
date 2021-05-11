package com.github.zmzhou.easyboot.framework.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 执行定时任务的线程池配置类
 *
 * @author zmzhou
 * @version 1.0
 * @title SchedulingConfig
 * @date 2020/12/15 21:24
 */
@EnableAsync
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
		// 获取处理器数量 Ncpu = CPU核心数
		int cpuNum = Runtime.getRuntime().availableProcessors();
		// 根据cpu数量,计算出合理的线程并发数
		// Nthreads = Ncpu x Ucpu x (1 + W/C)，其中 Ucpu = CPU使用率，0~1；W/C = 等待时间与计算时间的比率
		int threadNum = cpuNum * 2;
		// 定时任务执行线程池核心线程数
		taskScheduler.setPoolSize(threadNum);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setThreadNamePrefix("Easy-boot-taskScheduler-");
		//用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		//该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		taskScheduler.setAwaitTerminationSeconds(30);
		return taskScheduler;
	}
}
