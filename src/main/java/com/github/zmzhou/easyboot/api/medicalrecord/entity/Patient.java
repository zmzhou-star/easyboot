/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 病人信息实体类 patient
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Data
@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 6041583888300023522L;

    /** 姓名 */
    private String userName;

    /** 手机号码 */
    private String tel;

    /** 性别（1男 0女 2未知） */
    private String sex;

    /** 出生日期 */
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDate;

    /** 出生地 */
    private String birthplace;

    /** 常住地址 */
    private String permanentAddress;

    /** 职业 */
    private String career;

    /** 婚育史 */
    private String marriage;

    /** 主诉 */
    private String chiefComplaint;

    /** 治法方药 */
    private String medicines;
}
