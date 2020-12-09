package com.github.zmzhou.easyboot.framework.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.github.zmzhou.easyboot.api.monitor.service.SysOperLogService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

import lombok.extern.slf4j.Slf4j;


/**
 * The type Global controller advice.
 *
 * @param <T> the type parameter
 * @author zmzhou
 * @description GlobalControllerAdvice
 * @date 2020 /07/03 13:56
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice<T> implements ResponseBodyAdvice<T> {
	/** 正则表达式匹配[\d] */
	private static final Pattern PATTERN = Pattern.compile("\\[(\\d)]");
	@Resource
	private SysOperLogService operLogService;

	/**
	 * 方法描述
	 * @author zmzhou
	 * @date 2020/08/29 14:42
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	/**
	 * 返回值过滤器
	 * @author zmzhou
	 * @date 2020/08/29 14:42
	 */
	@Override
	public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
							 Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
							 ServerHttpResponse response) {
		HttpServletResponse res = ServletUtils.getRequestAttributes().getResponse();
		if (null != res){
			HttpSession session = ServletUtils.getSession();
			// 设置Cookie;Secure;HttpOnly
			res.setHeader( "Set-Cookie", "JSESSIONID=" + session.getId() + "; Secure; HttpOnly");
			// 设置X-Frame-Options 只要包含在框架中的站点与为页面提供服务的站点相同，仍然可以在框架中使用该页面
			res.setHeader("X-Frame-Options", "DENY");
			// 当检测到反射的XSS攻击时阻止加载页面：
			res.setHeader("X-XSS-Protection", "1; mode=block");
		}
		return body;
	}
	
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(BaseException.class)
	public ApiResult<Object> baseException(BaseException e) {
		String msg = e.getErrMsg();
		// 替换返回信息中的[0]占位符
		if (e.getParams()!=null){
			for (int i = 0; i < e.getParams().length; i++) {
				Matcher m = PATTERN.matcher(msg);
				while (m.find()){
					msg = msg.replace(m.group(1), String.valueOf(e.getParams()[i]));
				}
			}
		}
		log.error(msg, e);
		// 记录操作失败日志
		saveFailOperLog(e, msg);
		return new ApiResult<>().error(e.getCode(), msg);
	}
	/**
	 * Exception handler response.
	 *
	 * @param e the e
	 * @return the response
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ApiResult<Object> exceptionHandler(Exception e) {
		String uri = ServletUtils.getRequest().getRequestURI();
		log.error("[exceptionHandler] {} Catch an exception:", uri, e);
		// 记录操作失败日志
		saveFailOperLog(e, e.getMessage());
		return ApiResult.badRequest();
	}
	
	/**
	 * 参数验证异常
	 */
	@ExceptionHandler(BindException.class)
	public ApiResult<Object> validatedBindException(BindException e) {
		String uri = ServletUtils.getRequest().getRequestURI();
		log.error(e.getMessage() + " {}", uri, e);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		// 记录操作失败日志
		saveFailOperLog(e, message);
		return new ApiResult<>().error(message);
	}
	
	/**
	 * 接口访问权限异常 
	 * @param e AccessDeniedException
	 * @return ApiResult<Object>
	 * @author zmzhou
	 * @date 2020/12/9 15:13
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ApiResult<Object> accessDeniedExceptionHandler(AccessDeniedException e) {
		String uri = ServletUtils.getRequest().getRequestURI();
		String message = e.getMessage();
		log.error("[accessDeniedExceptionHandler] {} Catch an exception:", uri, e);
		// 记录操作失败日志
		saveFailOperLog(e, message);
		return new ApiResult<>().error(message);
	}
	
	/**
	 * 参数验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Object> validExceptionHandler(MethodArgumentNotValidException e) {
		String uri = ServletUtils.getRequest().getRequestURI();
		log.error(e.getMessage() + " {}", uri, e);
		String message = "";
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null != fieldError){
			message = fieldError.getDefaultMessage();
		}
		// 记录操作失败日志
		saveFailOperLog(e, message);
		return new ApiResult<>().error(message);
	}

	/**
	 * 记录操作失败日志
	 * @param e exception
	 * @param message 错误信息
	 * @author zmzhou
	 * @date 2020/9/16 21:04
	 */
	private void saveFailOperLog(Exception e, String message) {
		// 记录操作失败日志
		StackTraceElement st = e.getStackTrace()[0];
		operLogService.saveOperLog(ServletUtils.getRequest(), st.getClass(), st.getMethodName(), new Object[]{},
				null, Constants.ZERO, message);
	}
}
