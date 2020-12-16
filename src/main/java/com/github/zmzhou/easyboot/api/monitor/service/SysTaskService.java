package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.Date;

import javax.annotation.Resource;

import org.quartz.CronExpression;
import org.springframework.cache.annotation.CacheConfig;
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
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 定时任务Service接口
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Service
@CacheConfig(cacheNames = {"monitor:SysTask"})
@Transactional(rollbackFor = Exception.class)
public class SysTaskService {
	@Resource
	private SysTaskDao dao;

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
	 * 新增定时任务
	 *
	 * @param entity 定时任务
	 * @return SysTask 新增结果
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	public SysTask save(SysTask entity) {
		// cron表达式校验
		if (!CronExpression.isValidExpression(entity.getCronExpression())) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "cron表达式不正确");
		}
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUsername());
		return dao.saveAndFlush(entity);
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
		// cron表达式校验
		if (!CronExpression.isValidExpression(entity.getCronExpression())) {
			throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "cron表达式不正确");
		}
		entity.setUpdateTime(new Date());
		entity.setUpdateBy(SecurityUtils.getUsername());
		return dao.saveAndFlush(entity);
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
		update(task);
		return task.getId();
	}
}
