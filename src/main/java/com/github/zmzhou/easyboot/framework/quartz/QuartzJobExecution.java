package com.github.zmzhou.easyboot.framework.quartz;

import org.quartz.JobExecutionContext;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行定时任务（允许并发执行）
 *
 * @author zmzhou
 * @version 1.0
 * @title QuartzJobExecution
 * @date 2020/12/17 18:38
 */
@Slf4j
public class QuartzJobExecution extends BaseQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysTask sysTask) throws Exception {
		log.info("并发执行定时任务:{}", sysTask.getJobName());
		ScheduleUtils.invokeMethod(sysTask);
	}
}
