package com.github.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.system.entity.SysNotice;

/**
 * 通知公告信息数据访问层接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
public interface SysNoticeDao extends JpaRepository<SysNotice, Long>, JpaSpecificationExecutor<SysNotice> {

}
