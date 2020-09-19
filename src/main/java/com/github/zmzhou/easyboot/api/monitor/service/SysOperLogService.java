package com.github.zmzhou.easyboot.api.monitor.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.zmzhou.easyboot.api.monitor.dao.SysOperLogDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysOperLog;
import com.github.zmzhou.easyboot.api.monitor.excel.SysOperLogExcel;
import com.github.zmzhou.easyboot.api.monitor.vo.SysOperLogParams;
import com.github.zmzhou.easyboot.api.system.controller.LoginController;
import com.github.zmzhou.easyboot.api.system.service.BaseService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.ThreadPoolUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;
import com.github.zmzhou.easyboot.framework.vo.IpInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统操作日志记录service
 * @title SysOperLogService
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/14 23:09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOperLogService extends BaseService<SysOperLogParams> {
	/** 数据库允许的参数最大长度，超过截取 */
	private static final int MAX_PARAMS_LENGTH = 2048;

	/**
	 * 操作日志记录开关
	 */
	@Value("${server.operate-log.enabled}")
	private boolean enabled;

	@Resource
	private TokenService tokenService;
	@Resource
	private SysOperLogDao operLogDao;

	/**
	 * 保存操作日志信息
	 *
	 * @param request  HttpServletRequest
	 * @param clazz   记录日志的类
	 * @param method  记录日志的方法
	 * @param args    参数
	 * @param result  执行结果
	 * @param status  成功状态
	 * @param msg     错误信息
	 * @author zmzhou
	 * @date 2020/9/13 19:00
	 */
	public void saveOperLog(HttpServletRequest request, String clazz, String method, Object[] args,
	                         Object result, String status, String msg) {
		// 操作日志记录开关
		if (!enabled){
			return;
		}
		// 获取用户身份信息
		LoginUser loginUser = tokenService.getLoginUser(request);
		// 登录成功前为空
		if (loginUser == null) {
			loginUser = new LoginUser();
			tokenService.setUserAgent(loginUser);
		}
		LoginUser finalLoginUser = loginUser;
		String requestMethod = request.getMethod();
		StringBuffer requestUrl = request.getRequestURL();
		// 交给线程池执行
		ThreadPoolUtils.execute(() -> {
			String methodDesc = null;
			String title = null;
			try {
				Class<?> clz = Class.forName(clazz);
				// 登录方法包含用户密码敏感信息，不记录日志
				if (LoginController.class.equals(clz)) {
					return;
				}
				// 类注解获取模块标题
				Api clzAnno = AnnotationUtils.findAnnotation(clz, Api.class);
				if (clzAnno != null) {
					title = Arrays.toString(clzAnno.tags()).replaceAll("[\\[\\]]", "");
				}
				methodDesc = this.getMethodDesc(clz, method);
			} catch (ClassNotFoundException e) {
				log.error("类找不到异常", e);
			}
			// 用户定位信息
			IpInfo ipInfo = finalLoginUser.getIpInfo();
			// 设置操作日志信息
			SysOperLog operLog = SysOperLog.builder()
					// 模块标题
					.title(title)
					// 方法名称
					.method(clazz + Constants.DOT + method)
					// 方法描述
					.methodDesc(methodDesc)
					// 操作人员
					.operName(finalLoginUser.getUsername())
					// 请求方式 POST/GET
					.requestMethod(requestMethod)
					// 请求URL
					.operUrl(requestUrl.toString())
					// 请求参数
					.operParam(StringUtils.substring(JSON.toJSONString(args), 0, MAX_PARAMS_LENGTH))
					// 操作结果  超长字段处理
					.operResult(StringUtils.substring(JSON.toJSONString(result), 0, MAX_PARAMS_LENGTH))
					.operIp(ipInfo.getIp())
					.operLocation(ipInfo.getAddr())
					// 操作状态
					.status(status)
					// 错误消息
					.errorMsg(StringUtils.substring(msg, 0, MAX_PARAMS_LENGTH))
					.operTime(new Date())
					.build();
			// 保存操作日志
			operLogDao.saveAndFlush(operLog);
		});
	}

	/**
	 * Gets method desc.
	 *
	 * @param clz    the clz
	 * @param method the method
	 * @return the method desc
	 */
	private String getMethodDesc(Class<?> clz, String method) {
		String methodDesc = null;
		Method[] methods = clz.getDeclaredMethods();
		// 遍历查找方法
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				// 方法注解获取方法描述
				ApiOperation methodAnno = AnnotationUtils.findAnnotation(m, ApiOperation.class);
				if (methodAnno != null) {
					methodDesc = methodAnno.value();
				}
				break;
			}
		}
		return methodDesc;
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
