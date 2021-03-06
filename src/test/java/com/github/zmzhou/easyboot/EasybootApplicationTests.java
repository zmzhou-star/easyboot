package com.github.zmzhou.easyboot;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.vo.PageParams;

/**
 * The type Easyboot application tests.
 */
@SpringBootTest
@WebAppConfiguration
@Transactional
@WithMockUser(username="admin", roles={"admin"})
public class EasybootApplicationTests {

    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(EasybootApplicationTests.class);
    /** junit */
    protected static final String JUNIT = "junit";
    /** pd */
    protected static final String PD = SecurityUtils.sha256Encrypt("Zmzhou.1324");
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
    void contextLoads() throws NoSuchAlgorithmException {
        Assertions.assertNotNull(params);
        log.info("测试");
        // 生成一个Base64唯一字符串
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey desKey = keygen.generateKey();
        log.info(Base64.encodeBase64URLSafeString(desKey.getEncoded()));
    }

}
