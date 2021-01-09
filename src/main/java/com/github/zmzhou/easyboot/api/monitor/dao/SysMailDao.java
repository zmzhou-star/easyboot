package com.github.zmzhou.easyboot.api.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.monitor.entity.SysMail;

/**
 * 系统邮件记录数据访问层接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2021-01-09 20:27:00
 */
public interface SysMailDao extends JpaRepository<SysMail, Long>, JpaSpecificationExecutor<SysMail> {

}
