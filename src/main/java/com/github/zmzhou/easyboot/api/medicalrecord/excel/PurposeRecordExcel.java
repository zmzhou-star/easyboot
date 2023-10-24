/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;

import lombok.Data;

/**
 * 看诊记录导出excel类 purpose_record
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class PurposeRecordExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 1344545337879330485L;

    /** 病人ID */
    @ExcelProperty(value = {EXCEL_NAME, "", "病人ID"})
    private Long patientId;

    /** 主诉 */
    @ExcelProperty(value = {EXCEL_NAME, "", "主诉"})
    private String chiefComplaint;

    /** 治法方药 */
    @ExcelProperty(value = {EXCEL_NAME, "", "治法方药"})
    private String medicines;
}
