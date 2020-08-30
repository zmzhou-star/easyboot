package com.zmzhou.easyboot.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author zmzhou
 * @description 时间工具类
 * @date 2020/07/02 16:52
 */
public final class DateUtils {
	/**
	 * yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	private static final String[] PARSE_PATTERNS = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
	
	/**
	 * 私有构造器
	 * @author zmzhou
	 * @date 2020/08/29 14:30
	 */
	private DateUtils() {
	}
	/**
	 * 获取当前Date型日期
	 *
	 * @return Date() 当前日期
	 */
	public static Date now() {
		return new Date();
	}
	
	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getDate() {
		return DateFormatUtils.format(now(), YYYY_MM_DD);
	}
	
	/**
	 * 日期型字符串转化为日期格式
	 * @param str
	 * @return  日期
	 * @author zmzhou
	 * @date 2020/08/29 14:32
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), PARSE_PATTERNS);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取服务器启动时间
	 * @return Date
	 * @author zmzhou
	 * @date 2020/08/29 14:31
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}
	
	/**
	 * 计算两个时间差
	 * @author zmzhou
	 * @date 2020/08/29 14:31
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nm = 1000L * 60;
		long nh = nm * 60;
		long nd = nh * 24;
		// long ns = 1000
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns
		return day + "天" + hour + "小时" + min + "分钟";
	}
}
