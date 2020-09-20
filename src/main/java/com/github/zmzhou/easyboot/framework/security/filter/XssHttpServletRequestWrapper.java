package com.github.zmzhou.easyboot.framework.security.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.EasyBootUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 防御XSS(Cross Site Scripting)跨站脚本攻击 过滤所有请求参数
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/20 22:48
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 请求参数
	 */
	private final Map<String, String[]> params = new HashMap<>();

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		//将参数表，赋予给当前的Map以便于持有request中的参数
		Map<String, String[]> requestMap = request.getParameterMap();
		this.params.putAll(requestMap);
		this.modifyParameterValues();
	}

	/**
	 * Gets parameter.
	 *
	 * @param name the name
	 * @return the parameter
	 */
	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		if (StringUtils.isNotBlank(parameter)) {
			//apache的common-lang中的StringEscapeUtils 这个类已经过期了
			//这里使用的apache的common-text中的转义html方法
			return StringEscapeUtils.escapeHtml4(parameter.trim());
		}
		return null;
	}

	/**
	 * Get parameter values string [ ].
	 *
	 * @param parameter the parameter
	 * @return the string [ ]
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return new String[0];
		}
		int length = values.length;
		String[] escapeValues = new String[length];
		for (int i = 0; i < length; i++) {
			// 去除参数前后空格
			escapeValues[i] = StringEscapeUtils.escapeHtml4(values[i].trim());
		}
		return escapeValues;
	}

	/**
	 * 重写getInputStream方法  post类型的请求参数必须通过流才能获取到值
	 *
	 * @return the input stream
	 * @throws IOException the io exception
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		// 非json类型，直接返回
		String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
		if (!header.toLowerCase().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
			return super.getInputStream();
		}
		// 为空，直接返回
		String json = IOUtils.toString(super.getInputStream(), Constants.CHARSETS);
		if (StringUtils.isEmpty(json)) {
			return super.getInputStream();
		}
		log.debug("防御XSS,去除空格前参数：{}", json);
		Map<String, Object> map = EasyBootUtils.jsonStringToMap(json);
		log.debug("防御XSS,去除空格后参数：{}", JSON.toJSONString(map));
		ByteArrayInputStream bis = new ByteArrayInputStream(JSON.toJSONString(map).getBytes(Constants.CHARSETS));
		return new MyServletInputStream(bis);
	}


	/**
	 * 将parameter的值去除空格后重写回去
	 * Modify parameter values.
	 */
	public void modifyParameterValues() {
		Set<String> set = params.keySet();
		for (String key : set) {
			String[] values = params.get(key);
			values[0] = values[0].trim();
			params.put(key, values);
		}
	}
}

/**
 * The type My servlet input stream.
 */
class MyServletInputStream extends ServletInputStream {
	private final ByteArrayInputStream bis;

	/**
	 * Instantiates a new My servlet input stream.
	 *
	 * @param bis the bis
	 */
	public MyServletInputStream(ByteArrayInputStream bis) {
		this.bis = bis;
	}

	/**
	 * Is finished boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isFinished() {
		return true;
	}

	/**
	 * Is ready boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isReady() {
		return true;
	}

	/**
	 * Sets read listener.
	 *
	 * @param listener the listener
	 */
	@Override
	public void setReadListener(ReadListener listener) {
		// setReadListener
	}

	/**
	 * Read int.
	 *
	 * @return the int
	 */
	@Override
	public int read() {
		return bis.read();
	}
}
