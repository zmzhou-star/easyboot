/**
 * @Title GlobalControllerAdvice.java
 * @Package com.security.dpsapi.web.controller
 * @Description
 * @author White
 * @time 2019年9月12日 下午3:23:08
 * @version V1.0
 */
package com.zmzhou.easyboot.framework.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.zmzhou.easyboot.common.exception.BaseException;
import com.zmzhou.easyboot.framework.page.ApiResult;

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
	 * 方法描述
	 * @author zmzhou
	 * @date 2020/08/29 14:42
	 */
	@Override
	public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
							 Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
							 ServerHttpResponse response) {
		return body;
	}
	
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(BaseException.class)
	public ApiResult<Object> baseException(BaseException e) {
		log.error(e.getErrMsg(), e);
		return new ApiResult<>().error(e.getCode(), e.getErrMsg());
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
		log.error("[exceptionHandler] Catch an exception:", e);
		return ApiResult.badRequest();
	}
	
	/**
	 * 参数验证异常
	 */
	@ExceptionHandler(BindException.class)
	public ApiResult<Object> validatedBindException(BindException e) {
		log.error(e.getMessage(), e);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return new ApiResult<>().error(message);
	}
	
	/**
	 * 参数验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Object> validExceptionHandler(MethodArgumentNotValidException e) {
		log.error(e.getMessage(), e);
		String message = "";
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null != fieldError){
			message = fieldError.getDefaultMessage();
		}
		return new ApiResult<>().error(message);
	}
}
