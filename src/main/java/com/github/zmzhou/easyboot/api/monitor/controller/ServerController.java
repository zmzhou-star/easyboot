package com.github.zmzhou.easyboot.api.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.ServerInfo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.web.BaseController;

/**
 * @description 服务器监控
 * @author zmzhou
 * @date 2020/07/02 20:35
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
    /**
     * 获取服务器信息
     * @author zmzhou
     * @date 2020/07/03 14:28
     */
    @GetMapping("/getInfo")
    public ApiResult<ServerInfo> getInfo() {
        // 获取服务器信息
        ServerInfo info = new ServerInfo();
        return ok(info);
    }
}
