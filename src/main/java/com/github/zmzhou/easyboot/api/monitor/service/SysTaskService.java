package com.github.zmzhou.easyboot.api.monitor.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.monitor.dao.SysTaskDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.quartz.ScheduleUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务Service接口
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysTaskService {
	@Resource
	private SysTaskDao dao;
	@Resource
	private Scheduler scheduler;

	/**
	 * 项目启动时，初始化定时任务（注：不能手动修改数据库ID和任务组名，否则会导致脏数据） 
	 * @throws SchedulerException SchedulerException
	 * @author zmzhou
	 * @date 2020/12/18 17:44
	 */
	@PostConstruct
	public void init() throws SchedulerException {
		scheduler.clear();
		List<SysTask> taskList = dao.findAll();
		log.info("项目启动，初始化定时任务{}个", taskList.size());
		taskList.parallelStream().forEach(task -> ScheduleUtils.createScheduleJob(scheduler, task));
	}
	/**
	 * 分页查询定时任务数据
	 *
	 * @param params   查询参数
	 * @param pageable 分页
	 * @return Page<SysTask>
	 */
	public Page<SysTask> findAll(SysTaskParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc(Constants.STATUS));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysTask> spec = new SimpleSpecificationBuilder<SysTask>()
				.and(Constants.STATUS, Operator.EQUAL, params.getStatus())
				.and("jobGroup", Operator.EQUAL, params.getJobGroup())
				.and("jobName", Operator.LIKE, params.getJobName())
				.build();
		return dao.findAll(spec, page);
	}

	/**
	 * 根据id查询定时任务
	 *
	 * @param id 定时任务id
	 * @return SysTask对象
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	public SysTask findById(Long id) {
		if (null == id) {
			return new SysTask();
		}
		return dao.findById(id).orElse(new SysTask());
	}

	/**
	 * 根据bean名字、方法名、方法参数、cron表达式判断定时任务是否存在
	 * @param entity 定时任务信息
	 * @return 是否存在
	 * @author zmzhou
	 * @date 2020/12/19 15:05
	 */
	public boolean exists(SysTask entity) {
		Specification<SysTask> spec = new SimpleSpecificationBuilder<SysTask>(
				"beanName", Operator.EQUAL, entity.getBeanName())
				.and("methodName", Operator.EQUAL, entity.getMethodName())
				.and("methodParams", Operator.EQUAL, entity.getMethodParams())
				.and("cronExpression", Operator.EQUAL, entity.getCronExpression()).build();
		return dao.findOne(spec).isPresent();
	}
	
	/**
	 * 新增定时任务
	 *
	 * @param entity 定时任务
	 * @return SysTask 新增结果
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	public SysTask save(SysTask entity) {
		this.validated(entity);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUsername());
		// 保存定时任务信息
		entity = dao.saveAndFlush(entity);
		// 创建定时任务
		ScheduleUtils.createScheduleJob(scheduler, entity);
		return entity;
	}

	/**
	 * 修改定时任务
	 *
	 * @param entity 定时任务
	 * @return SysTask 修改结果
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	public SysTask update(SysTask entity) {
		this.validated(entity);
		entity.setUpdateTime(new Date());
		entity.setUpdateBy(SecurityUtils.getUsername());
		// 删除旧的定时任务，再更新数据，创建新的定时任务
		SysTask task = findById(entity.getId());
		try {
			// 判断是否存在
			JobKey jobKey = ScheduleUtils.getJobKey(task.getId(), task.getJobGroup());
			if (scheduler.checkExists(jobKey)) {
				log.info("删除旧的定时任务:{}，再更新数据", jobKey);
				scheduler.deleteJob(jobKey);
			}
		} catch (SchedulerException e) {
			log.error("删除旧的定时任务异常：{}", task, e);
		}
		entity = dao.saveAndFlush(entity);
		// 创建定时任务
		ScheduleUtils.createScheduleJob(scheduler, entity);
		return entity;
	}

	/**
	 * 校验定时任务字段信息是否正确 
	 * @param entity 定时任务信息
	 * @author zmzhou
	 * @date 2020/12/18 12:07
	 */
	private void validated(SysTask entity) {
		if (null == entity.getId() && exists(entity)) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "存在相同定时任务");
		}
		// 判断spring上下文是否存在bean
		if (!SpringUtils.getContext().containsBean(entity.getBeanName())) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "bean名字不存在");
		}
		Object bean = SpringUtils.getContext().getBean(entity.getBeanName());
		String methodParams = entity.getMethodParams();
		String methodName = entity.getMethodName();
		try {
			Method method;
			if (StringUtils.isNotBlank(methodParams)) {
				method = bean.getClass().getDeclaredMethod(methodName, String.class);
			} else {
				method = bean.getClass().getDeclaredMethod(methodName);
			}
			log.info("方法名：{}", method.getName());
		} catch (NoSuchMethodException e) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "方法名不存在");
		}
		// cron表达式校验
		if (!CronExpression.isValidExpression(entity.getCronExpression())) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "cron表达式不正确");
		}
	}

	/**
	 * 批量删除定时任务
	 *
	 * @param ids 需要删除的定时任务ID
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	public void deleteByIds(Long[] ids) {
		for (Long id : ids) {
			// 根据用户id删除数据
			dao.deleteById(id);
			// 删除定时任务
			SysTask task = findById(id);
			try {
				scheduler.deleteJob(ScheduleUtils.getJobKey(task.getId(), task.getJobGroup()));
			} catch (SchedulerException e) {
				log.error("删除定时任务异常：{}", task, e);
			}
		}
	}

	/**
	 * 根据id修改任务状态 
	 * @param id id
	 * @param status 任务状态
	 * @return 修改结果
	 * @author zmzhou
	 * @date 2020/12/16 22:22
	 */
	public Long changeStatus(Long id, String status) {
		SysTask task = findById(id);
		task.setStatus(status);
		task.setUpdateTime(new Date());
		task.setUpdateBy(SecurityUtils.getUsername());
		task = dao.saveAndFlush(task);
		// 定时任务JobKey
		JobKey jobKey = ScheduleUtils.getJobKey(task.getId(), task.getJobGroup());
		try {
			if (Constants.PAUSE.equals(status)) {
				// 定时任务暂停
				scheduler.pauseJob(jobKey);
			} else {
				// 定时任务运行
				scheduler.resumeJob(jobKey);
			}
		} catch (SchedulerException e) {
			log.error("修改定时任务运行状态异常：{}", status, e);
		}
		return task.getId();
	}

	/**
	 * 定时任务立即执行一次 
	 * @param id 定时任务id
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/12/18 19:14
	 */
	public long run(Long id) {
		SysTask task = findById(id);
		// 参数
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(Constants.TASK_PROPERTIES, task);
		try {
			log.info("定时任务立即执行一次:{}", task.getJobName());
			scheduler.triggerJob(ScheduleUtils.getJobKey(task.getId(), task.getJobGroup()), dataMap);
		} catch (SchedulerException e) {
			log.error("定时任务立即执行一次异常:{}", task, e);
		}
		return task.getId();
	}
}
