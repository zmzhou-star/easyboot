package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.monitor.dao.SysOperLogDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysOperLog;
import com.github.zmzhou.easyboot.api.monitor.excel.SysOperLogExcel;
import com.github.zmzhou.easyboot.api.monitor.vo.SysOperLogParams;
import com.github.zmzhou.easyboot.api.system.service.BaseService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 系统操作日志记录表service
 * @title SysOperLogService
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/14 23:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOperLogService extends BaseService<SysOperLogParams> {
	@Resource
	private SysOperLogDao operLogDao;

	/**
	 * 保存操作日志
	 *
	 * @param operLog 操作日志信息
	 * @author zmzhou
	 * @date 2020/9/13 19:00
	 */
	public void saveOperLog(SysOperLog operLog) {
		// 保存操作日志
		operLog.setOperTime(new Date());
		// 超长字段处理
		operLog.setOperResult(StringUtils.substring(operLog.getOperResult(), 0, 2048));
		operLogDao.saveAndFlush(operLog);
	}

	/**
	 * 获取登录日志记录列表
	 * @param params 查询参数
	 * @return 列表数据
	 * @author zmzhou
	 * @date 2020/9/13 21:23
	 */
	public Page<SysOperLog> findAll(SysOperLogParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc("operTime"));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysOperLog> spec = new SimpleSpecificationBuilder<SysOperLog>()
				.and("title", Operator.LIKE, params.getTitle())
				.and("operName", Operator.LIKE, params.getOperName())
				.and("requestMethod", Operator.EQUAL, params.getRequestMethod())
				.and( Constants.STATUS, Operator.EQUAL, params.getStatus())
				.between("and", "operTime", params.getBeginTime(), params.getEndTime())
				.build();
		return operLogDao.findAll(spec, page);
	}

	/**
	 * 删除登录日志
	 * @param ids id数组
	 * @author zmzhou
	 * @date 2020/9/13 21:39
	 */
	public void delete(Long[] ids) {
		for (Long id: ids) {
			operLogDao.deleteById(id);
		}
	}

	/**
	 * 清空登录日志
	 * @author zmzhou
	 * @date 2020/9/13 21:40
	 */
	public void cleanLoginLog() {
		operLogDao.deleteAllInBatch();
	}

	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return excel文件路径名
	 * @author zmzhou
	 * @date 2020/9/3 22:59
	 */
	@Override
	public String export(SysOperLogParams params) throws InterruptedException {
		Page<SysOperLog> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		while (list.hasNext()) {
			dataConversion(list, excelList, SysOperLogExcel.class);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, SysOperLogExcel.class);
		return excelUtils.download(excelList, SysOperLogExcel.class, params.getExcelName());
	}

}
