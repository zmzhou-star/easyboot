package com.github.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.system.entity.SysConfig;

/**
 * 参数配置数据访问层接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
public interface SysConfigDao extends JpaRepository<SysConfig, Long>, JpaSpecificationExecutor<SysConfig> {

}
