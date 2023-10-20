package com.github.zmzhou.easyboot.common.utils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 时间工具类
 *
 * @author zmzhou
 * @date 2020/07/02 16:52
 */
@Slf4j
public final class DateUtils {
	/**
	 * yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 文件下载的文件名称
	 */
	public static final String FILE_NAME_PATTERN = "yyyyMMddHHmmss";

	private static final String[] PARSE_PATTERNS = {
			YYYY_MM_DD, YYYY_MM_DD_HH_MM_SS, "yyyy-MM-dd HH:mm", "yyyy-MM",
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
	 * Gets time.
	 * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * @return the time
	 */
	public static String getTime() {
		return DateFormatUtils.format(now(), YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 日期型字符串转化为日期格式
	 * @param str 日期型字符串
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
		    log.error("parse date:{} error", str, e);
			return null;
		}
	}

	/**
	 * 获取当前时间戳
	 *
	 * @return long
	 * @author zmzhou
	 * @since 2021/8/22 13:58
	 */
	public static long currentTimeMillis() {
	    return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
