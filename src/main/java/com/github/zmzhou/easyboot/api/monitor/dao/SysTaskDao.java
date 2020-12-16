package com.github.zmzhou.easyboot.api.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;

/**
 * 定时任务数据访问层接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
public interface SysTaskDao extends JpaRepository<SysTask, Long>, JpaSpecificationExecutor<SysTask> {

}
