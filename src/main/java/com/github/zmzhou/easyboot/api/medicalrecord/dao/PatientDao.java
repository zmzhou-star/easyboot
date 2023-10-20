/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.medicalrecord.entity.Patient;

/**
 * 病人信息数据访问层接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
public interface PatientDao extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

}
