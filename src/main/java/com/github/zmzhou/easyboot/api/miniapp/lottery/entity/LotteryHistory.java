/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 彩票历史数据实体类 lottery_history
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022-07-03 20:57:38
 */
@Data
@Entity
@Table(name = "lottery_history")
public class LotteryHistory extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 5130678831896931916L;

    /** 期号 */
    private Long lotteryId;

    /** 彩票类型 */
    private String lotteryType;

    /** 销售金额 */
    private String salesAmount;

    /** 开奖日期 */
    @JSONField(format = "yyyy-MM-dd")
    private Date lotteryDate;

    /** 红球1 */
    private String red1;

    /** 红球2 */
    private String red2;

    /** 红球3 */
    private String red3;

    /** 红球4 */
    private String red4;

    /** 红球5 */
    private String red5;

    /** 红球6 */
    private String red6;

    /** 红球1 */
    private String blue1;

    /** 蓝球2 */
    private String blue2;

    /** 一等奖数量 */
    private String prizeNum1;

    /** 一等奖奖金 */
    private String prizeBonus1;

    /** 二等奖数量 */
    private String prizeNum2;

    /** 二等奖奖金 */
    private String prizeBonus2;

    /** 三等奖数量 */
    private String prizeNum3;

    /** 三等奖奖金 */
    private String prizeBonus3;
}
