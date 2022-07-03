/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.miniapp.lottery.entity.LotteryHistory;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 彩票历史数据vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022-07-03 21:04:47
 */
@Data
@ApiModel(description = "彩票历史数据vo类")
public class LotteryHistoryVo extends LotteryHistory implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -8432828163009641761L;

	/**
	 * vo转实体类.
	 *
	 * @return the LotteryHistory 实体类
	 */
	@Override
	public LotteryHistory toEntity() {
		LotteryHistory entity = new LotteryHistory();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
