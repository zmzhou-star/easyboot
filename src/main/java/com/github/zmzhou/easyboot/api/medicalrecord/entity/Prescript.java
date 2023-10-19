/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 药方实体类 prescript
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Data
@Entity
@Table(name = "prescript")
public class Prescript extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 7339244521078871964L;
    
    /** 药方名 */
    private String prescriptName;

    /** 用途 */
    private String purpose;

    /** 治法方药 */
    private String medicines;
}
