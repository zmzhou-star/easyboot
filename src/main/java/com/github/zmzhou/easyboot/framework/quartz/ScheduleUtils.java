package com.github.zmzhou.easyboot.framework.quartz;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务工具类
 *
 * @author zmzhou
 * @version 1.0
 * @title ScheduleUtils
 * @date 2020/12/17 19:35
 */
@Slf4j
public class ScheduleUtils {

	/**
	 * 执行定时任务方法
	 *
	 * @param sysTask 定时任务
	 * @author zmzhou
	 * @date 2020/12/17 19:21
	 */
	public static void invokeMethod(SysTask sysTask) throws Exception {
		String methodName = sysTask.getMethodName();
		String methodParams = sysTask.getMethodParams();
		Object bean = SpringUtils.getContext().getBean(sysTask.getBeanName());
		if (StringUtils.isNotBlank(methodParams)) {
			Method method = bean.getClass().getDeclaredMethod(methodName, String.class);
			method.invoke(bean, methodParams);
		} else {
			Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}
	/**
	 * 定时任务TriggerKey 
	 * @param taskId 任务id
	 * @param jobGroup 任务组
	 * @return TriggerKey
	 * @author zmzhou
	 * @date 2020/12/18 18:45
	 */
	public static TriggerKey getTriggerKey(Long taskId, String jobGroup) {
		return TriggerKey.triggerKey(Constants.TASK_CLASS_NAME + taskId, jobGroup);
	}

	/**
	 * 定时任务JobKey 
	 * @param taskId 任务id
	 * @param jobGroup 任务组
	 * @return JobKey
	 * @author zmzhou
	 * @date 2020/12/18 18:44
	 */
	public static JobKey getJobKey(Long taskId, String jobGroup) {
		return JobKey.jobKey(Constants.TASK_CLASS_NAME + taskId, jobGroup);
	}

	/**
	 * 创建定时任务 
	 * @param scheduler Scheduler
	 * @param task 定时任务信息
	 * @author zmzhou
	 * @date 2020/12/18 18:28
	 */
	public static void createScheduleJob(Scheduler scheduler, SysTask task) {
		Class<? extends Job> jobClass = getQuartzJobClass(task);
		// 构建job信息
		Long taskId = task.getId();
		String jobGroup = task.getJobGroup();
		JobKey jobKey = getJobKey(taskId, jobGroup);
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();
		// 表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(task, cronScheduleBuilder);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(taskId, jobGroup))
				.withSchedule(cronScheduleBuilder).build();
		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(Constants.TASK_PROPERTIES, task);
		try {
			// 判断是否存在
			if (scheduler.checkExists(jobKey)) {
				// 防止创建时存在数据问题 先移除，然后在执行创建操作
				scheduler.deleteJob(jobKey);
			}
			scheduler.scheduleJob(jobDetail, trigger);
			// 暂停任务
			if (Constants.PAUSE.equals(task.getStatus())) {
				scheduler.pauseJob(jobKey);
			}
		} catch (SchedulerException e) {
			log.error("创建定时任务异常：{}", task, e);
		}
	}
	/**
	 * 得到quartz任务类
	 *
	 * @param sysTask 执行计划
	 * @return 具体执行任务类
	 */
	private static Class<? extends Job> getQuartzJobClass(SysTask sysTask) {
		if (Constants.ONE.equals(sysTask.getConcurrent())) {
			return QuartzJobExecution.class;
		}
		return QuartzDisallowConcurrentExecution.class;
	}

	/**
	 * 设置定时任务策略
	 */
	private static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysTask task, CronScheduleBuilder cb) {
		switch (task.getMisfirePolicy()) {
			case Constants.MISFIRE_IGNORE_MISFIRES:
				return cb.withMisfireHandlingInstructionIgnoreMisfires();
			case Constants.MISFIRE_FIRE_AND_PROCEED:
				return cb.withMisfireHandlingInstructionFireAndProceed();
			case Constants.MISFIRE_DO_NOTHING:
				return cb.withMisfireHandlingInstructionDoNothing();
			default:
				return cb;
		}
	}
}
