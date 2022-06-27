package com.github.zmzhou.easyboot.framework.redis;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.common.Constants;

import lombok.SneakyThrows;

/**
 * @author zmzhou
 * @title RedisUtils
 * @Description spring redis 工具类
 * @Date 2020/07/03 15:18
 */
@Component
public final class RedisUtils {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key   缓存的键值
	 * @param value 缓存的值
	 * @return 缓存的对象
	 */
	public <T> ValueOperations<String, T> set(String key, T value) {
		ValueOperations<String, T> operation = opsForValue();
		operation.set(key, value);
		return operation;
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key      缓存的键值
	 * @param value    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 * @return 缓存的对象
	 */
	public <T> ValueOperations<String, T> set(String key, T value, Integer timeout, TimeUnit timeUnit) {
		ValueOperations<String, T> operation = opsForValue();
		operation.set(key, value, timeout, timeUnit);
		return operation;
	}

	/**
	 * 获得缓存的基本对象。
	 *
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	@SneakyThrows
	public <T> T get(String key) {
        ValueOperations<String, T> operation = opsForValue();
        T value = operation.get(key);
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                array.set(i, obj.toJavaObject(Class.forName(obj.getString(Constants.JSON_TYPE))));
            }
        } else if (value instanceof JSONObject) {
            JSONObject obj = (JSONObject) value;
            value = obj.toJavaObject((Type) Class.forName(obj.getString(Constants.JSON_TYPE)));
        }
        return value;
    }

	/**
	 * 删除单个对象
	 * @param key 缓存键
	 * @author zmzhou
	 * @date 2020/11/17 11:43
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 根据缓存键集合删除缓存集合
	 * @param keys 缓存键集合
	 * @author zmzhou
	 * @date 2020/11/17 11:43
	 */
	public void delete(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 获得缓存的缓存键集合列表
	 *
	 * @param pattern 字符串前缀
	 * @return 缓存键集合列表
	 */
	public Collection<String> keys(String pattern) {
		return redisTemplate.keys(pattern + "*");
	}
	/**
	 * 获取ValueOperations
	 * @return ValueOperations
	 * @author zmzhou
	 * @date 2020/07/09 10:28
	 */
	@SuppressWarnings("unchecked")
	public <T> ValueOperations<String, T> opsForValue() {
		return (ValueOperations<String, T>) redisTemplate.opsForValue();
	}
}
