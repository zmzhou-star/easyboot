/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.monitor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.vo.RedisCacheVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * redis缓存监控
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022/3/20 13:09
 */
@Api(tags = {"redis缓存监控"})
@RestController
@RequestMapping("/monitor/cache")
public class RedisCacheController extends BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取redis缓存统计信息
     *
     * @return com.github.zmzhou.easyboot.framework.page.ApiResult<com.github.zmzhou.easyboot.api.monitor.vo.RedisCacheVo>
     * @author zmzhou
     * @since 2022/3/20 13:51
     */
    @PreAuthorize("@ebpe.hasPermission('monitor:cache:list')")
    @ApiOperation(value = "获取redis缓存统计信息")
    @GetMapping()
    public ApiResult<RedisCacheVo> getCacheInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>)
            connection -> connection.info("commandstats"));
        Long dbSize = (Long) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

        RedisCacheVo cacheVo = new RedisCacheVo();
        cacheVo.setInfo(info);
        cacheVo.setDbSize(dbSize);
        List<RedisCacheVo.CommandStatVo> statVos = new ArrayList<>();
        Optional.ofNullable(commandStats).map(command -> commandStats.stringPropertyNames())
            .ifPresent(props -> props.forEach(key -> {
                RedisCacheVo.CommandStatVo commandStatVo = new RedisCacheVo.CommandStatVo();
                String value = commandStats.getProperty(key);
                commandStatVo.setName(StringUtils.removeStart(key, "cmdstat_"));
                commandStatVo.setValue(StringUtils.substringBetween(value, "calls=", ",usec"));
                commandStatVo.setUsec(StringUtils.substringBetween(value, "usec=", ",usec_per_call"));
                commandStatVo.setUsecPerCall(StringUtils.substringAfter(value, "usec_per_call="));
                statVos.add(commandStatVo);
            }));
        cacheVo.setCommandStats(statVos);
        return ok(cacheVo);
    }

    /**
     * 获取redis缓存信息列表
     *
     * @param cacheKey 缓存key
     * @return com.github.zmzhou.easyboot.framework.page.ApiResult<java.util.Map<java.lang.String,java.lang.Object>>
     * @author zmzhou
     * @since 2022/3/20 14:56
     */
    @PreAuthorize("@ebpe.hasPermission('monitor:cache:data')")
    @ApiOperation(value = "获取redis缓存信息列表")
    @GetMapping("/getCacheData")
    public ApiResult<Map<String, Object>> getCacheData(@ApiParam(name = "cacheKey", value = "缓存名") String cacheKey) {
        Collection<String> keys = redisUtils.keys(StringUtils.trimToEmpty(cacheKey));
        Map<String, Object> result = new HashMap<>();
        Optional.ofNullable(keys).ifPresent(keySet -> keySet.stream()
            .filter(key -> !StringUtils.startsWith(key, "spring:session"))
            .forEach(key -> {
                if (StringUtils.contains(key, Constants.DOUBLE_COLON)) {
                    String str = StringUtils.substringBefore(key, Constants.DOUBLE_COLON);
                    String value = StringUtils.substringAfter(key, Constants.DOUBLE_COLON);
                    List<String> list = new ArrayList<>();
                    if (result.containsKey(str)) {
                        list.addAll((List<String>) result.get(str));
                    }
                    list.add(value);
                    result.put(str, list);
                } else {
                    result.put(key, redisUtils.get(key).toString());
                }
            }));
        return ok(result);
    }
}
