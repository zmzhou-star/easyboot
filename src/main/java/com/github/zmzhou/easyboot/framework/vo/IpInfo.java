package com.github.zmzhou.easyboot.framework.vo;

import java.io.Serializable;
import java.util.regex.Pattern;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * IpInfo
 * 用户ip定位信息
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/12 15:03
 */
@Data
public class IpInfo implements Serializable {
	/**
	 * The constant 获取IP地址URL.
	 */
	public static final String GET_IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
	/** 获取IP地址信息正文的正则 */
	public static final Pattern INFO_PATTERN = Pattern.compile("^.*(\\{\".*\"}).*$");

	/** serialVersionUID */
	private static final long serialVersionUID = -3241877307905588232L;
	/** ip地址 */
	private String ip;
	/** 省份 */
	private String pro;
	/** 省份代码 */
	private String proCode;
	/** 城市 */
	private String city;
	/** 城市代码 */
	private String cityCode;
	/** 地区 */
	private String region;
	/** 地区代码 */
	private String regionCode;
	/** 地址 */
	private String addr;
	/** 地区名 */
	private String regionNames;
	/** 错误信息 */
	private String err;
}
