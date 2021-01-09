package com.github.zmzhou.easyboot.api.monitor.excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;
import com.github.zmzhou.easyboot.common.excel.converters.StatusConverter;

import lombok.Data;

/**
 * 系统邮件记录导出excel类 sys_mail
 *
 * @author zmzhou
 * @version 1.0
 * date 2021-01-09 20:27:00
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysMailExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = 7699945949477655262L;
    /** 邮件发送人 */
    @ExcelProperty(value = {EXCEL_NAME, "", "邮件发送人"})
    private String from;

    /** 邮件接收人（多个邮箱则用分号&quot;;&quot;隔开） */
    @ExcelProperty(value = {EXCEL_NAME, "", "邮件接收人"})
    private String to;

    /** 邮件主题 */
    @ExcelProperty(value = {EXCEL_NAME, "", "邮件主题"})
    private String subject;

    /** 邮件内容 */
    @ExcelProperty(value = {EXCEL_NAME, "", "邮件内容"})
    private String text;

    /** 抄送 */
    @ExcelProperty(value = {EXCEL_NAME, "", "抄送"})
    private String cc;

    /** 密送 */
    @ExcelProperty(value = {EXCEL_NAME, "", "密送"})
    private String bcc;

    /** 状态 */
    @ExcelProperty(value = {EXCEL_NAME, "", "状态"}, converter = StatusConverter.class)
    private String status;

    /** 报错信息 */
    @ExcelProperty(value = {EXCEL_NAME, "", "报错信息"})
    private String error;

    /** 发送时间 */
    @DateTimeFormat(DATE_PATTERN)
    @ExcelProperty(value = {EXCEL_NAME, "", "发送时间"})
    private Date sendDate;
}
