package com.github.zmzhou.easyboot.api.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

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
public class SysDictExcel extends SysDictTypeExcel {
    /** serialVersionUID */
    private static final long serialVersionUID = 391839308764304718L;

    /** 字典标签 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典标签"})
    @ColumnWidth(25)
    private String dictLabel;

    /** 字典键值 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典键值"})
    private String dictValue;
    
    /** 字典排序 */
    @ExcelProperty(value = {EXCEL_NAME, "", "字典排序"})
    private Integer dictSort;

}
