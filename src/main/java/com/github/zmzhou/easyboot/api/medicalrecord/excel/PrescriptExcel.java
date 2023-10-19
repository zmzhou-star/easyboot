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
 * 药方导出excel类 prescript
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class PrescriptExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 7333104785150108664L;

    /** 药方名 */
    @ExcelProperty(value = {EXCEL_NAME, "", "药方名"})
    private String prescriptName;

    /** 用途 */
    @ExcelProperty(value = {EXCEL_NAME, "", "用途"})
    private String purpose;

    /** 治法方药 */
    @ExcelProperty(value = {EXCEL_NAME, "", "治法方药"})
    private String medicines;
}
