package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.monitor.dao.SysLoginLogDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;
import com.github.zmzhou.easyboot.api.monitor.excel.SysLoginLogExcel;
import com.github.zmzhou.easyboot.api.monitor.vo.SysLoginLogParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.ThreadPoolUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.service.TokenService;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;
import com.github.zmzhou.easyboot.framework.vo.PageParams;

/**
 * 系统登录日志记录表service
 *
 * @author zmzhou
 * @version 1.0
 * @title SysLoginLogService
 * @date 2020/9/13 18:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLoginLogService extends BaseService<SysLoginLogParams> {
	@Resource
	private SysLoginLogDao loginLogDao;
	@Resource
	private TokenService tokenService;

	/**
	 * 保存登录成功日志
	 *
	 * @param loginUser 用户登录信息
	 * @author zmzhou
	 * @date 2020/9/13 19:00
	 */
	public void saveSuccessLog(LoginUser loginUser) {
		SysLoginLog loginLog = createSysLoginLog(loginUser);
		loginLog.setMsg("登录成功");
		loginLog.setStatus(Constants.ONE);
		// 保存登录成功日志
		ThreadPoolUtils.execute(() -> loginLogDao.saveAndFlush(loginLog));
	}

	/**
	 * 保存登录失败的日志
	 *
	 * @param msg 失败信息
	 * @param userName 用户名
	 * @author zmzhou
	 * @date 2020/9/13 20:41
	 */
	public void saveFailLog(String msg, String userName) {
		LoginUser loginUser = new LoginUser();
		tokenService.setUserAgent(loginUser);
		SysLoginLog loginLog = createSysLoginLog(loginUser);
		loginLog.setUserName(userName);
		loginLog.setMsg(msg);
		loginLog.setStatus(Constants.ZERO);
		// 保存登录失败的日志
		ThreadPoolUtils.execute(() -> loginLogDao.saveAndFlush(loginLog));
	}

	/**
	 * 设置登录日志信息
	 *
	 * @param loginUser 用户登录信息
	 * @return SysLoginLog
	 * @author zmzhou
	 * @date 2020/9/13 20:42
	 */
	private SysLoginLog createSysLoginLog(LoginUser loginUser) {
		SysLoginLog loginLog = new SysLoginLog();
		BeanUtils.copyProperties(loginUser, loginLog);
		loginLog.setUserName(loginUser.getUsername());
		loginLog.setLoginTime(new Date());
		return loginLog;
	}

	/**
	 * 获取登录日志记录列表
	 * @param params 查询参数
	 * @return 列表数据
	 * @author zmzhou
	 * @date 2020/9/13 21:23
	 */
	public Page<SysLoginLog> findAll(SysLoginLogParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc(Constants.LOGIN_TIME), Sort.Order.desc(Constants.STATUS));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysLoginLog> spec = new SimpleSpecificationBuilder<SysLoginLog>()
				.and("ipAddr", Operator.LIKE, params.getIpAddr())
				.and("userName", Operator.LIKE, params.getUserName())
				.and( Constants.STATUS, Operator.EQUAL, params.getStatus())
				.between("and", Constants.LOGIN_TIME, params.getBeginTime(), params.getEndTime())
				.build();
		return loginLogDao.findAll(spec, page);
	}

	/**
	 * 删除登录日志
	 * @param ids id数组
	 * @author zmzhou
	 * @date 2020/9/13 21:39
	 */
	public void delete(Long[] ids) {
		for (Long id: ids) {
			loginLogDao.deleteById(id);
		}
	}

	/**
	 * 清空登录日志
	 * @author zmzhou
	 * @date 2020/9/13 21:40
	 */
	public void cleanLoginLog() {
		loginLogDao.deleteAllInBatch();
	}

	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return excel文件路径名
	 * @author zmzhou
	 * @date 2020/9/3 22:59
	 */
	@Override
	public String export(SysLoginLogParams params) throws InterruptedException {
		Page<SysLoginLog> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		while (list.hasNext()) {
			dataConversion(list, excelList, SysLoginLogExcel.class);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, SysLoginLogExcel.class);
		return excelUtils.download(excelList, SysLoginLogExcel.class, params.getExcelName());
	}

	/**
	 * 根据登录时间统计用户登录信息
	 * @param params 查询参数
	 * @return 查询结果
	 * @author zmzhou
	 * @date 2020/12/20 16:54
	 */
	public List<SysLoginLog> stat(PageParams params) {
		// 构造查询条件
		Specification<SysLoginLog> spec = new SimpleSpecificationBuilder<SysLoginLog>()
				.and( Constants.STATUS, Operator.EQUAL, Constants.ONE)
				.between(Constants.LOGIN_TIME, params.getBeginTime(), params.getEndTime())
				.build();
		return loginLogDao.findAll(spec);
	}
}
