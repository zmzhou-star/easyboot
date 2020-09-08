package com.github.zmzhou.easyboot.common.exception;

import org.springframework.http.HttpStatus;

import com.github.zmzhou.easyboot.common.ErrorCode;

/**
 * The type Base exception.
 *
 * @author zmzhou
 * @title BaseException
 * @Description 描述
 * @Date 2020 /07/21 10:46
 */
public class BaseException extends RuntimeException {
	/**
	 * The constant serialVersionUID.
	 *
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -1310368837666726631L;
	/**
	 * 错误码
	 */
	private final int code;
	/**
	 * 错误消息
	 */
	private final String errMsg;
	/**
	 * 错误参数
	 */
	private final transient Object[] params;

	/**
	 * Instantiates a new Base exception.
	 *
	 * @param errMsg     the err msg
	 */
	public BaseException(String errMsg) {
		this.code = HttpStatus.BAD_REQUEST.value();
		this.errMsg = errMsg;
		this.params = new Object[] {};
	}
	
	/**
	 * Instantiates a new Base exception.
	 *
	 * @param errorCode the error code
	 */
	public BaseException(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.errMsg = errorCode.getMsg();
		this.params = new Object[] {};
	}
	
	/**
	 * Instantiates a new Base exception.
	 *
	 * @param errorCode the error code
	 * @param params    the params
	 */
	public BaseException(ErrorCode errorCode, Object... params) {
		this.code = errorCode.getCode();
		this.errMsg = errorCode.getMsg();
		this.params = params;
	}

	/**
	 * Instantiates a new Base exception.
	 *
	 * @param httpStatus the http status
	 * @param errMsg     the err msg
	 */
	public BaseException(HttpStatus httpStatus, String errMsg) {
		this.code = httpStatus.value();
		this.errMsg = errMsg;
		this.params = new Object[] {};
	}

	/**
	 * Instantiates a new Base exception.
	 *
	 * @param errorCode the error code
	 * @param errMsg    the err msg
	 * @param params    the params
	 */
	public BaseException(ErrorCode errorCode, String errMsg, Object... params) {
		this.code = errorCode.getCode();
		this.errMsg = errMsg;
		this.params = params;
	}

	/**
	 * Instantiates a new Base exception.
	 *
	 * @param code   the code
	 * @param errMsg the err msg
	 * @param params the params
	 */
	public BaseException(int code, String errMsg, Object... params) {
		this.code = code;
		this.errMsg = errMsg;
		this.params = params;
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Gets err msg.
	 *
	 * @return the err msg
	 */
	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * Get params object [ ].
	 *
	 * @return the object [ ]
	 */
	public Object[] getParams() {
		return params;
	}
}
