/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel3;

import lombok.Data;

/**
 * 彩票历史数据导出excel类 lottery_history
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022-07-03 21:04:47
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class LotteryHistoryExcel extends BaseExcel3 {
    /** serialVersionUID */
    private static final long serialVersionUID = -4865261086600160220L;

    /** 期号 */
    @ExcelProperty(value = {EXCEL_NAME, "", "期号"})
    private Long lotteryId;

    /** 彩票类型 */
    @ExcelProperty(value = {EXCEL_NAME, "", "彩票类型"})
    private String lotteryType;

    /** 开奖日期 */
    @DateTimeFormat(DATE_PATTERN)
    @ExcelProperty(value = {EXCEL_NAME, "", "开奖日期"})
    private Date lotteryDate;

    /** 红球1 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球1"})
    private String red1;

    /** 红球2 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球2"})
    private String red2;

    /** 红球3 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球3"})
    private String red3;

    /** 红球4 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球4"})
    private String red4;

    /** 红球5 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球5"})
    private String red5;

    /** 红球6 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球6"})
    private String red6;

    /** 红球1 */
    @ExcelProperty(value = {EXCEL_NAME, "", "红球1"})
    private String blue1;

    /** 蓝球2 */
    @ExcelProperty(value = {EXCEL_NAME, "", "蓝球2"})
    private String blue2;

    /** 一等奖数量 */
    @ExcelProperty(value = {EXCEL_NAME, "", "一等奖数量"})
    private String prizeNum1;

    /** 一等奖奖金 */
    @ExcelProperty(value = {EXCEL_NAME, "", "一等奖奖金"})
    private String prizeBonus1;

    /** 二等奖数量 */
    @ExcelProperty(value = {EXCEL_NAME, "", "二等奖数量"})
    private String prizeNum2;

    /** 二等奖奖金 */
    @ExcelProperty(value = {EXCEL_NAME, "", "二等奖奖金"})
    private String prizeBonus2;

    /** 三等奖数量 */
    @ExcelProperty(value = {EXCEL_NAME, "", "三等奖数量"})
    private String prizeNum3;

    /** 三等奖奖金 */
    @ExcelProperty(value = {EXCEL_NAME, "", "三等奖奖金"})
    private String prizeBonus3;
}
