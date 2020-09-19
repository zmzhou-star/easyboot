package com.github.zmzhou.easyboot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.vo.PageParams;

/**
 * The type Easyboot application tests.
 */
@SpringBootTest
@WebAppConfiguration
@Transactional
public class EasybootApplicationTests {
    
    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(EasybootApplicationTests.class);
    /** junit */
    protected static final String JUNIT = "junit";
    /** pd */
    protected static final String PD = SecurityUtils.sha256Encrypt(Constants.DEFAULT_PD);
    /** 默认分页参数 */
    protected static PageParams params;
    static {
        params = new PageParams();
        params.setStatus("1");
        params.setPageNum(1);
        params.setPageSize(10);
    }
    /**
     * Init.
     */
    @BeforeEach
    public void init() {
        log.info("开始测试...");
    }
    
    /**
     * After.
     */
    @AfterEach
    public void after() {
        log.info("测试结束...");
    }
    
    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
        Assertions.assertNotNull(params);
        log.info("测试");
    }

}
