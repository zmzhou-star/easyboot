package com.zmzhou.easyboot.framework.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author zmzhou
 * @title RedisCache
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
	public <T> T get(String key) {
		ValueOperations<String, T> operation = opsForValue();
		return operation.get(key);
	}
	
	/**
	 * 删除单个对象
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * 获得缓存的基本对象列表
	 *
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public Collection<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
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
