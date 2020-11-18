package com.github.zmzhou.easyboot.api.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;
import com.github.zmzhou.easyboot.common.excel.converters.StatusConverter;

import lombok.Data;

/**
 * 数据字典导出excel类 sys_dict
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysDictTypeExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 391839308764304718L;

    /** 字典名称 */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "字典名称"})
    private String dictName;

    /** 字典类型 */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "字典类型"})
    private String dictType;

    /** 状态（1正常 0停用） */
    @ExcelProperty(value = {EXCEL_NAME, "", "状态"}, converter = StatusConverter.class)
    private String status;
}
