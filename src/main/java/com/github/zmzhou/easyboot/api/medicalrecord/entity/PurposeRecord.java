/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 看诊记录实体类 purpose_record
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Data
@Entity
@Table(name = "purpose_record")
public class PurposeRecord extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = -5840793564053737390L;

    /** 病人ID */
    private Long patientId;

    /** 主诉 */
    private String chiefComplaint;

    /** 治法方药 */
    private String medicines;
}
