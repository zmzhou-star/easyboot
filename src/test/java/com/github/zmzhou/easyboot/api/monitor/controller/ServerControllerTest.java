package com.github.zmzhou.easyboot.api.monitor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.ServerInfo;
import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

/**
 * The type Server controller test.
 *
 * @author zmzhou
 * @version 1.0
 * @title ServerControllerTest
 * @description
 * @date 2020 /8/30 17:27
 */
class ServerControllerTest extends EasybootApplicationTests {

    /**
     * The Controller.
     */
    @Autowired
    private ServerController controller;

    /**
     * Gets info.
     */
    @Test
    void getInfo() {
        ApiResult<ServerInfo> res = controller.getInfo();
        Assertions.assertNotNull(res);
    }
}
