/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.miniapp.lottery.entity.LotteryHistory;

/**
 * 彩票历史数据数据访问层接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022-07-03 20:57:38
 */
public interface LotteryHistoryDao extends JpaRepository<LotteryHistory, Long>, JpaSpecificationExecutor<LotteryHistory> {
    /**
     * 根据期号查询彩票信息
     *
     * @param lotteryId 期号
     * @return com.github.zmzhou.easyboot.api.miniapp.lottery.entity.LotteryHistory
     * @author zmzhou
     * @since 2022/7/3 21:47
     */
    @Query(value="from LotteryHistory where lotteryId = ?1 order by updateTime desc")
    LotteryHistory selectByLotteryId(Long lotteryId);
}
