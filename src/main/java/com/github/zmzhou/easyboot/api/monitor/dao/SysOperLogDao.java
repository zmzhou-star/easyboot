package com.github.zmzhou.easyboot.api.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.monitor.entity.SysOperLog;

/**
 * 系统操作日志dao
 * @title SysOperLogDao
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/14 23:07
 */
public interface SysOperLogDao extends JpaRepository<SysOperLog, Long>, JpaSpecificationExecutor<SysOperLog> {
}
