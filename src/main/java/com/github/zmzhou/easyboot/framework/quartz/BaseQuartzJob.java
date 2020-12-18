package com.github.zmzhou.easyboot.framework.quartz;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.api.monitor.entity.SysTaskLog;
import com.github.zmzhou.easyboot.api.monitor.service.SysTaskLogService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象quartz job执行类
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/17 18:23
 */
@Slf4j
public abstract class BaseQuartzJob implements Job {

	/**
	 * 多线程 记录本线程开始结束时间
	 */
	private static final ThreadLocal<Date> DATE_THREAD_LOCAL = new ThreadLocal<>();

	@Override
	public void execute(JobExecutionContext context) {
		SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(Constants.TASK_PROPERTIES), sysTask);
		try {
			// 定时任务执行前
			DATE_THREAD_LOCAL.set(new Date());
			doExecute(context, sysTask);
			after(sysTask, null);
		} catch (Exception e) {
			log.error("执行定时任务异常 ：", e);
			after(sysTask, e);
		}
	}

	/**
	 * 定时任务执行后记录日志 
	 * @param sysJob 定时任务信息
	 * @param e Exception
	 * @author zmzhou
	 * @date 2020/12/17 18:39
	 */
	protected void after(SysTask sysJob, Exception e) {
		Date startTime = DATE_THREAD_LOCAL.get();
		DATE_THREAD_LOCAL.remove();

		SysTaskLog taskLog = new SysTaskLog();
		BeanUtils.copyProperties(sysJob, taskLog, "id");
		taskLog.setCreateTime(startTime);
		taskLog.setTimeConsuming(System.currentTimeMillis() - startTime.getTime());
		if (e != null) {
			taskLog.setStatus(Constants.ZERO);
			try (StringWriter sw = new StringWriter();) {
				e.printStackTrace(new PrintWriter(sw, true));
				String str = sw.toString();
				String errorMsg = StringUtils.substring(str, 0, 512);
				taskLog.setExceptionInfo(errorMsg);
			} catch (IOException ioException) {
				taskLog.setExceptionInfo(e.getMessage());
				log.error("获取错误原因异常:{}", e, ioException);
			}
		} else {
			taskLog.setStatus(Constants.ONE);
		}
		// 保存日志
		SpringUtils.getBean(SysTaskLogService.class).save(taskLog);
	}

	/**
	 * 执行定时任务方法
	 *
	 * @param context 工作执行上下文对象
	 * @param sysTask  系统计划任务
	 * @throws Exception 执行过程中的异常
	 * @author zmzhou
	 * @date 2020/12/17 18:36
	 */
	protected abstract void doExecute(JobExecutionContext context, SysTask sysTask) throws Exception;
}
