package com.github.zmzhou.easyboot.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程工具类
 *
 * @author zmzhou
 * @version 1.0
 * @title ThreadPoolUtils
 * @date 2020/9/13 18:08
 */
@Slf4j
public final class ThreadPoolUtils {
	/**
	 * ThreadPoolExecutor
	 */
	private static ThreadPoolExecutor threadPool = null;

	/**
	 * 工具类私有化构造器
	 * @author zmzhou
	 * @date 2020/9/13 18:20
	 */
	private ThreadPoolUtils() {
	}

	/**
	 * 无返回值直接执行
	 *
	 * @param runnable Runnable
	 * @author zmzhou
	 * @date 2020/9/13 18:09
	 */
	public static void execute(Runnable runnable) {
		get().execute(runnable);
	}

	/**
	 * 有返回值直接执行
	 *
	 * @param callable Callable
	 * @return Future<T> future.get()获取返回值
	 * @author zmzhou
	 * @date 2020/9/13 18:10
	 */
	public static <T> Future<T> submit(Callable<T> callable) {
		return get().submit(callable);
	}

	/**
	 * 获取线程池对象
	 * @return ThreadPoolExecutor
	 * @author zmzhou
	 * @date 2020/9/13 18:21
	 */
	private static ThreadPoolExecutor get() {
		if (threadPool != null) {
			log.info("线程池已创建");
			return threadPool;
		} else {
			synchronized (ThreadPoolUtils.class) {
				//二次检查
				if (threadPool == null) {
					// 获取处理器数量
					int cpuNum = Runtime.getRuntime().availableProcessors();
					// 根据cpu数量,计算出合理的线程并发数
					int threadNum = cpuNum * 2 + 1;
					// 创建线程池
					threadPool = new ThreadPoolExecutor(
							// 核心线程数
							threadNum - 1,
							// 最大线程数
							threadNum,
							// 闲置线程存活时间
							Integer.MAX_VALUE,
							// 时间单位
							TimeUnit.MILLISECONDS,
							// 线程队列
							new LinkedBlockingDeque<>(Integer.MAX_VALUE),
							// 线程工厂
							new ThreadFactoryBuilder().setNameFormat("easy-thread-%d").build(),
							// 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略
							new ThreadPoolExecutor.CallerRunsPolicy()
					);
					log.info("创建线程池完成:{}", threadPool.toString());
				}
			}
		}
		return threadPool;
	}
}
