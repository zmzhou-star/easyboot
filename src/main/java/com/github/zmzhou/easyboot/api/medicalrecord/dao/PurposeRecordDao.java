/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.medicalrecord.entity.PurposeRecord;

/**
 * 看诊记录数据访问层接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
public interface PurposeRecordDao extends JpaRepository<PurposeRecord, Long>, JpaSpecificationExecutor<PurposeRecord> {
}
