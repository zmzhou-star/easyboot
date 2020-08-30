package com.zmzhou.easyboot.framework.entity.server;

import com.zmzhou.easyboot.common.utils.Arith;

/**
 * The type Mem.
 *
 * @author zmzhou
 * @description 內存相关信息
 * @date 2020 /07/02 20:42
 */
public class Mem {
	/**
	 * 内存总量
	 */
	private double total;
	
	/**
	 * 已用内存
	 */
	private double used;
	
	/**
	 * 剩余内存
	 */
	private double free;
	
	/**
	 * Gets total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return Arith.div(total, (1024 * 1024 * 1024), 2);
	}
	
	/**
	 * Sets total.
	 *
	 * @param total the total
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	
	/**
	 * Gets used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return Arith.div(used, (1024 * 1024 * 1024), 2);
	}
	
	/**
	 * Sets used.
	 *
	 * @param used the used
	 */
	public void setUsed(long used) {
		this.used = used;
	}
	
	/**
	 * Gets free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return Arith.div(free, (1024 * 1024 * 1024), 2);
	}
	
	/**
	 * Sets free.
	 *
	 * @param free the free
	 */
	public void setFree(long free) {
		this.free = free;
	}
	
	/**
	 * Gets usage.
	 *
	 * @return the usage
	 */
	public double getUsage() {
		return Arith.mul(Arith.div(used, total, 4), 100);
	}
}
