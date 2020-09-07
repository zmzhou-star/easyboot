package com.zmzhou.easyboot.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.zmzhou.easyboot.EasybootApplicationTests;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @title FileUtilTest
 * @description
 * @author zmzhou
 * @version 1.0
 * @date 2020/8/30 21:28
 */
class FileUtilTest {

    @Test
    void isValidFilename() {
        Assertions.assertFalse(FileUtil.isValidFilename("admin/123.png"));
        Assertions.assertTrue(FileUtil.isValidFilename("admin/1#23.png"));
    }

    @Test
    void deleteFile() {
        Assertions.assertFalse(FileUtil.deleteFile("/admin/1#23.png"));
    }
}