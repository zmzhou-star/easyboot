package com.github.zmzhou.easyboot.api.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;

import lombok.Data;

/**
 * 参数配置导出excel类 sys_config
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysConfigExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = -784745088185377773L;
    /** 参数名称 */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "参数名称"})
    private String configName;

    /** 参数键名 */
    @ExcelProperty(value = {EXCEL_NAME, "", "参数键名"})
    @ColumnWidth(20)
    private String configKey;

    /** 参数键值 */
    @ExcelProperty(value = {EXCEL_NAME, "", "参数键值"})
    private String configValue;

    /** 系统内置（Y是 N否） */
    @ExcelProperty(value = {EXCEL_NAME, "", "系统内置（Y是 N否）"})
    private String configType;

}
