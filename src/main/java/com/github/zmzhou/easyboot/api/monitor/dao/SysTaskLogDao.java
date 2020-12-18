package com.github.zmzhou.easyboot.api.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTaskLog;

/**
 * 定时任务日志数据访问层接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
public interface SysTaskLogDao extends JpaRepository<SysTaskLog, Long>, JpaSpecificationExecutor<SysTaskLog> {

}
