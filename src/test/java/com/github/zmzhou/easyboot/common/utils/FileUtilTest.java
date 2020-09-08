package com.github.zmzhou.easyboot.common.utils;

import org.junit.jupiter.api.Test;

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
        assertFalse(FileUtil.isValidFilename("admin/123.png"));
        assertTrue(FileUtil.isValidFilename("admin/1#23.png"));
    }

    @Test
    void deleteFile() {
        assertFalse(FileUtil.deleteFile("/admin/1#23.png"));
    }
}
