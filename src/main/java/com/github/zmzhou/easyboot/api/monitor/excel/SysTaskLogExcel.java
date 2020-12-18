package com.github.zmzhou.easyboot.api.monitor.excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.excel.converters.SuccessConverter;

import lombok.Data;

/**
 * 定时任务日志导出excel类 sys_task_log
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysTaskLogExcel extends BaseExcel {
    /** serialVersionUID */
    private static final long serialVersionUID = 4026507797742371739L;
    /** 任务名称 */
    @ExcelProperty(value = {EXCEL_NAME, "", "任务名称"})
    private String jobName;

    /** 任务分组 */
    @ExcelProperty(value = {EXCEL_NAME, "", "任务分组"})
    private String jobGroup;

    /** bean名字 */
    @ExcelProperty(value = {EXCEL_NAME, "", "bean名字"})
    private String beanName;

    /** 类的方法名 */
    @ExcelProperty(value = {EXCEL_NAME, "", "类的方法名"})
    private String methodName;

    /** 类的方法参数 */
    @ExcelProperty(value = {EXCEL_NAME, "", "方法参数"})
    private String methodParams;

    /** cron表达式 */
    @ExcelProperty(value = {EXCEL_NAME, "", "cron表达式"})
    private String cronExpression;

    /** 耗时 */
    @ExcelProperty(value = {EXCEL_NAME, "", "耗时(毫秒)"})
    private Long timeConsuming;

    /** 执行状态（1正常 0失败） */
    @ExcelProperty(value = {EXCEL_NAME, "", "执行状态"}, converter = SuccessConverter.class)
    private String status;

    /** 异常信息 */
    @ExcelProperty(value = {EXCEL_NAME, "", "异常信息"})
    private String exceptionInfo;
    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "创建时间"})
    @DateTimeFormat(DATE_PATTERN)
    private Date createTime;

    /**
     * Gets create time.
     *
     * @return the create time
     */
    public Date getCreateTime() {
        if (null == this.createTime) {
            return null;
        }
        return (Date) (this.createTime).clone();
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Date createTime) {
        if (null == createTime) {
            this.createTime = null;
        } else {
            this.createTime = (Date) (createTime).clone();
        }
    }
}
