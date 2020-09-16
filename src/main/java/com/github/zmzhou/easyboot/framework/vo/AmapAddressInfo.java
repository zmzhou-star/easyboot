package com.github.zmzhou.easyboot.framework.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.github.zmzhou.easyboot.common.Constants;

import lombok.Data;

/**
 * 高德地图 IP定位查询的响应结果的格式由请求参数output指定。
 *
 * @author zmzhou
 * @version 1.0
 * @title AmapIpInfo
 * @date 2020/9/16 21:36
 */
@Data
public class AmapAddressInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5120485000861199661L;
	/**
	 * 返回结果状态值
	 * <p>
	 * 值为0或1,0表示失败；1表示成功
	 */
	private String status;
	/**
	 * 返回状态说明
	 * <p>
	 * 返回状态说明，status为0时，info返回错误原因，否则返回“OK”。
	 */
	private String info;
	/**
	 * 状态码
	 * <p>
	 * 返回状态说明,10000代表正确,详情参阅info状态表
	 */
	private String infocode;
	/**
	 * 省份名称
	 * <p>
	 * 若为直辖市则显示直辖市名称；
	 * <p>
	 * 如果在局域网 IP网段内，则返回“局域网”；
	 * <p>
	 * 非法IP以及国外IP则返回空
	 */
	private String province;
	/**
	 * 城市名称
	 * <p>
	 * 若为直辖市则显示直辖市名称；
	 * <p>
	 * 如果为局域网网段内IP或者非法IP或国外IP，则返回空
	 */
	private String city;
	/**
	 * 城市的adcode编码
	 */
	private String adcode;
	/**
	 * 所在城市矩形区域范围 所在城市范围的左下右上对标对
	 */
	private String rectangle;
	/**
	 * 所在城市中心经纬度坐标
	 */
	private String centerCoordinates;

	/**
	 * 获取中心经纬度坐标
	 *
	 * @return 中心经纬度坐标
	 * @author zmzhou
	 * @date 2020/9/16 21:42
	 */
	public String getCenterCoordinates() {
		if (StringUtils.isBlank(this.rectangle)){
			return "";
		}
		String[] coors = this.rectangle.split(Constants.SEMICOLON);
		String[] c1 = coors[0].split(Constants.COMMA);
		String[] c2 = coors[1].split(Constants.COMMA);
		return getAverage(c1[0], c2[0]) + Constants.COMMA + getAverage(c1[1], c2[1]);
	}

	/**
	 * 计算坐标平均数
	 * @param a 数值a
	 * @param b 数值b
	 * @return 平均数
	 * @author zmzhou
	 * @date 2020/9/16 21:52
	 */
	public static BigDecimal getAverage(String a, String b) {
		return new BigDecimal(a).add(new BigDecimal(b)).divide(new BigDecimal(2));
	}
}
