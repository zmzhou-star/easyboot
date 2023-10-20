/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;

import lombok.Data;

/**
 * 病人信息导出excel类 patient
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class PatientExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 3025617422432667195L;

    /** 姓名 */
    @ExcelProperty(value = {EXCEL_NAME, "", "姓名"})
    private String userName;

    /** 手机号码 */
    @ExcelProperty(value = {EXCEL_NAME, "", "手机号码"})
    private String tel;

    /** 性别（1男 0女 2未知） */
    @ExcelProperty(value = {EXCEL_NAME, "", "性别（1男 0女 2未知）"})
    private String sex;

    /** 出生日期 */
    @DateTimeFormat(DATE_PATTERN)
    @ExcelProperty(value = {EXCEL_NAME, "", "出生日期"})
    private Date birthDate;

    /** 出生地 */
    @ExcelProperty(value = {EXCEL_NAME, "", "出生地"})
    private String birthplace;

    /** 常住地址 */
    @ExcelProperty(value = {EXCEL_NAME, "", "常住地址"})
    private String permanentAddress;

    /** 职业 */
    @ExcelProperty(value = {EXCEL_NAME, "", "职业"})
    private String career;

    /** 婚育史 */
    @ExcelProperty(value = {EXCEL_NAME, "", "婚育史"})
    private String marriage;

    /** 主诉 */
    @ExcelProperty(value = {EXCEL_NAME, "", "主诉"})
    private String chiefComplaint;

    /** 治法方药 */
    @ExcelProperty(value = {EXCEL_NAME, "", "治法方药"})
    private String medicines;
}
