package com.zmzhou.easyboot.framework.entity.server;

import java.lang.management.ManagementFactory;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.zmzhou.easyboot.common.utils.Arith;
import com.zmzhou.easyboot.common.utils.DateUtils;

/**
 *  @title Jvm
 *  @Description JVM相关信息
 *  @author zmzhou
 *  @Date 2020/08/29 15:01
 */
public class Jvm {
	/**
	 * 当前JVM占用的内存总数(M)
	 */
	private double total;
	
	/**
	 * JVM最大可用内存总数(M)
	 */
	private double max;
	
	/**
	 * JVM空闲内存(M)
	 */
	private double free;
	
	/**
	 * JDK版本
	 */
	private String version;
	
	/**
	 * JDK路径
	 */
	private String home;
	
	/**
	 * Gets total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return Arith.div(total, (1024 * 1024), 2);
	}
	
	/**
	 * Sets total.
	 *
	 * @param total the total
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
	/**
	 * Gets max.
	 *
	 * @return the max
	 */
	public double getMax() {
		return Arith.div(max, (1024 * 1024), 2);
	}
	
	/**
	 * Sets max.
	 *
	 * @param max the max
	 */
	public void setMax(double max) {
		this.max = max;
	}
	
	/**
	 * Gets free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return Arith.div(free, (1024 * 1024), 2);
	}
	
	/**
	 * Sets free.
	 *
	 * @param free the free
	 */
	public void setFree(double free) {
		this.free = free;
	}
	
	/**
	 * Gets used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return Arith.div(total - free, (1024 * 1024), 2);
	}
	
	/**
	 * Gets usage.
	 *
	 * @return the usage
	 */
	public double getUsage() {
		return Arith.mul(Arith.div(total - free, total, 4), 100);
	}
	
	/**
	 * 获取JDK名称
	 *
	 * @return the name
	 */
	public String getName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}
	
	/**
	 * Gets version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Sets version.
	 *
	 * @param version the version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * Gets home.
	 *
	 * @return the home
	 */
	public String getHome() {
		return home;
	}
	
	/**
	 * Sets home.
	 *
	 * @param home the home
	 */
	public void setHome(String home) {
		this.home = home;
	}
	
	/**
	 * JDK启动时间
	 *
	 * @return the start time
	 */
	public String getStartTime() {
		return DateFormatUtils.format(DateUtils.getServerStartDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * JDK运行时间
	 *
	 * @return the run time
	 */
	public String getRunTime() {
		return DateUtils.getDatePoor(DateUtils.now(), DateUtils.getServerStartDate());
	}
}
