package com.zmzhou.easyboot.api.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zmzhou.easyboot.framework.entity.Server;
import com.zmzhou.easyboot.framework.page.ApiResult;
import com.zmzhou.easyboot.framework.web.BaseController;

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
    public ApiResult<Server> getInfo() {
        ApiResult<Server> result = new ApiResult<>();
        Server server = new Server();
        server.copyTo();
        result.setData(server);
        return result;
    }
}
