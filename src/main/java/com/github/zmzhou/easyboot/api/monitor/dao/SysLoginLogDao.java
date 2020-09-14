package com.github.zmzhou.easyboot.api.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;

/**
 * 登录日志dao
 * @title SysLoginLogDao
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 18:55
 */
public interface SysLoginLogDao extends JpaRepository<SysLoginLog, Long>, JpaSpecificationExecutor<SysLoginLog> {
}
