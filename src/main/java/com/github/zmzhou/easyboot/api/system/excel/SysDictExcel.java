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
public class SysDictExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 391839308764304718L;
    /** 字典排序 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典排序"})
    private Integer dictSort;

    /** 字典标签 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典标签"})
    private String dictLabel;

    /** 字典键值 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典键值"})
    private String dictValue;

    /** 字典名称 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典名称"})
    private String dictName;

    /** 字典类型 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典类型"})
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @ExcelProperty(value = {EXCEL_NAME, "", "样式属性（其他样式扩展）"})
    private String cssClass;

    /** 表格回显样式 */
    @ExcelProperty(value = {EXCEL_NAME, "", "表格回显样式"})
    private String listClass;

    /** 状态（1正常 0停用） */
    @ExcelProperty(value = {EXCEL_NAME, "", "状态（1正常 0停用）"}, converter = StatusConverter.class)
    private String status;

}
