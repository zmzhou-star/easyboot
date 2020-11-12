package com.github.zmzhou.easyboot.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.common.Constants;

/**
 * 工具类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020/9/19 17:20
 */
public final class EasyBootUtils {
	/**
	 * 构造器
	 *
	 * @author zmzhou
	 * date 2020/9/19 17:20
	 */
	private EasyBootUtils() {
		// 构造器
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
	 *
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String convertToCamelCase(String name) {
		// 快速检查
		if (StringUtils.isBlank(name)) {
			// 没必要转换
			return "";
		} else if (!name.contains(Constants.UNDERLINE)) {
			// 不含下划线，仅将首字母大写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		StringBuilder result = new StringBuilder();
		// 用下划线将原始字符串分割
		String[] camels = name.split(Constants.UNDERLINE);
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 驼峰式命名法 例如：user_name->userName
	 */
	public static String toCamelCase(String s) {
		if (StringUtils.isBlank(s)) {
			return "";
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Constants.UNDERLINE.equals(String.valueOf(c))) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Gets method.
	 *
	 * @param clazz  the clazz
	 * @param method the method
	 * @return the method
	 */
	public static Method getMethod(Class<?> clazz, String method) {
		Method[] methods = clazz.getDeclaredMethods();
		// 遍历查找方法
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				return m;
			}
		}
		return null;
	}


	/**
	 * Json string to map map.
	 *
	 * @param jsonString the json string
	 * @return the map
	 */
	public static Map<String, Object> jsonStringToMap(String jsonString) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(jsonString)){
			return map;
		}
		JSONObject jsonObject = JSON.parseObject(jsonString);
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof JSONArray) {
				List<Object> list = new ArrayList<>();
				for (Object obj : (JSONArray) value) {
					// 如果内层是JSONArray并且里面是JSONObject
					if (obj instanceof JSONObject) {
						list.add(jsonStringToMap(obj.toString()));
					} else {
						list.add(obj);
					}
				}
				map.put(key, list);
			} else if (value instanceof JSONObject) {
				// 如果内层是json对象的话，继续解析
				map.put(key, jsonStringToMap(value.toString()));
			} else {
				// 如果内层是普通对象的话，直接放入map中
				if (value instanceof String) {
					//这里使用的apache的common-text中的转义html方法
					map.put(key, StringEscapeUtils.escapeHtml4(value.toString().trim()));
				} else {
					map.put(key, value);
				}
			}
		}
		return map;
	}
}
