/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.miniapp.lottery.entity.LotteryHistory;
import com.github.zmzhou.easyboot.api.miniapp.lottery.service.LotteryService;
import com.github.zmzhou.easyboot.api.miniapp.lottery.vo.LotteryHistoryParams;
import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 彩票
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022/7/2 18:00
 */
@Api(tags = {"彩票管理"})
@RestController
@RequestMapping("/lottery/history")
public class LotteryController extends BaseController {
    @Autowired
    private LotteryService lotteryService;

    /**
     * 根据id查询彩票历史数据
     *
     * @param id ID
     * @return com.github.zmzhou.easyboot.framework.page.ApiResult<com.github.zmzhou.easyboot.api.system.entity.SysMenu>
     * @author zmzhou
     * @since 2022/7/17 18:55
     */
    @PreAuthorize("@ebpe.hasPermission('miniapp.lottery:history:query')")
    @GetMapping(value = {"{id}"})
    @ApiOperation(value = "根据id查询彩票历史数据")
    public ApiResult<SysMenu> getOne(@PathVariable(value = "id", required = false) Long id) {
        return ok(lotteryService.findById(id));
    }

    /**
     * 查询彩票历史数据列表
     *
     * @param params 查询参数
     * @return ApiResult<TableDataInfo>
     * @author zmzhou
     * date 2022-07-03 21:09:35
     */
    @PreAuthorize("@ebpe.hasPermission('miniapp.lottery:history:list')")
    @ApiOperation(value = "查询彩票历史数据列表")
    @PostMapping(path = "/list")
    public ApiResult<TableDataInfo> list(@RequestBody(required = false) LotteryHistoryParams params) {
        Pageable pageable = getPageable(params);
        Page<LotteryHistory> list = lotteryService.findAll(params, pageable);
        return ok(list);
    }

    /**
     * 导出excel
     *
     * @param params 查询参数
     * @return ApiResult<String> excel文件名
     * @author zmzhou
     * date 2022-07-03 21:09:35
     */
    @PreAuthorize("@ebpe.hasPermission('miniapp.lottery:history:export')")
    @PostMapping("/export")
    @ApiOperation(value = "导出彩票历史数据excel")
    public ApiResult<String> export(@RequestBody LotteryHistoryParams params) throws InterruptedException {
        return ok(lotteryService.export(params));
    }
}
