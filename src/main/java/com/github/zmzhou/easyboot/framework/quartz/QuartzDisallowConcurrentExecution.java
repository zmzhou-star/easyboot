package com.github.zmzhou.easyboot.framework.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行定时任务（禁止并发执行）
 *
 * @author zmzhou
 * @version 1.0
 * @title QuartzDisallowConcurrentExecution
 * @date 2020/12/17 18:36
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends BaseQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysTask sysTask) throws Exception {
		log.info("禁止并发执行定时任务:{}", sysTask.getJobName());
		ScheduleUtils.invokeMethod(sysTask);
	}
}
