package com.github.zmzhou.easyboot.common.utils;

import java.io.File;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type File util test.
 *
 * @author zmzhou
 * @version 1.0
 * @title FileUtilTest
 * @description
 * @date 2020 /8/30 21:28
 */
class FileUtilTest {

    /**
     * Is valid filename.
     */
    @Test
    void isValidFilename() {
        assertFalse(FileUtil.isValidFilename("admin/123.png"));
        assertTrue(FileUtil.isValidFilename("admin/1#23.png"));
    }

    /**
     * Delete file.
     */
    @Test
    void deleteFile() {
        assertFalse(FileUtil.deleteFile("/admin/1#23.png", true));
        assertFalse(FileUtil.deleteFile("/admin/1#3.png", false));
    }

    /**
     * Exists and mkdirs.
     */
    @Test
    void existsAndMkdirs() {
        FileUtil.existsAndMkdirs("/opt/eboot/downloadPath/");
        FileUtil.existsAndMkdirs("/opt/eboot/downloadPath/temp");
        FileUtil.delete(new File("/opt/eboot/downloadPath/temp"));
        assertFalse(FileUtil.exists("/admin/1#3.png"));
    }

    /**
     * Gets file suffix.
     */
    @Test
    void getFileSuffix() {
        assertEquals(".png", FileUtil.getFileSuffix("23/png"));
        assertEquals("", FileUtil.getFileSuffix(""));
    }
}
