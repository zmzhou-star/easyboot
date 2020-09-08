package com.github.zmzhou.easyboot.framework.page;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.github.zmzhou.easyboot.common.ErrorCode;

import lombok.Data;

/**
 * @description 返回值对象
 * @author zmzhou
 * @date 2020/07/02 17:11
 */
@Data
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 消息状态码 */
    private int code = HttpStatus.OK.value();
    /** 消息内容 */
    private String msg = HttpStatus.OK.getReasonPhrase();
    /** 列表数据 */
    private T data;
    
    public ApiResult() {
    }
    
    /**
     * @description 错误请求
     * @author zmzhou
     * @date 2020/07/03 14:12
     */
    public static <T> ApiResult<T> badRequest() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMsg(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return result;
    }
    public static <T> ApiResult<T> builder() {
        return new ApiResult<>();
    }
    
    /**
     * 请求失败设置失败信息
     * @param errorCode 错误码
     * @return
     * @author zmzhou
     * @date 2020/07/08 14:05
     */
    public ApiResult<T> error(ErrorCode errorCode) {
        this.setCode(errorCode.getCode());
        setMsg(errorCode.getMsg());
		return this;
	}
    /**
     * 请求参数错误设置失败信息
     * @param errorMsg 错误信息
     * @return
     * @author zmzhou
     * @date 2020/07/08 14:05
     */
    public ApiResult<T> error(String errorMsg) {
        this.error(ErrorCode.PARAM_ERROR);
        if (StringUtils.isNotBlank(errorMsg)) {
            setMsg(errorMsg);
        }
		return this;
	}
    /**
     * 请求错误设置失败信息
     * @param code 消息状态码
     * @param errorMsg 错误信息
     * @return
     * @author zmzhou
     * @date 2020/07/08 14:05
     */
    public ApiResult<T> error(int code, String errorMsg) {
        setCode(code);
        if (StringUtils.isNotBlank(errorMsg)) {
            setMsg(errorMsg);
        }
		return this;
	}
    /**
     * 请求成功设置返回信息
     * @param msg 返回信息
     * @return
     * @author zmzhou
     * @date 2020/07/08 14:05
     */
    public ApiResult<T> info(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            setMsg(msg);
        }
		return this;
	}
}
