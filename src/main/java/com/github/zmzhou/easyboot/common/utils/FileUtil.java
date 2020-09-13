package com.github.zmzhou.easyboot.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

/**
 * @title FileUtil
 * @description 文件处理工具类
 * @author zmzhou
 * @version 1.0
 * @date 2020/8/30 21:00
 */
@Slf4j
public final class FileUtil {
    /** 文件名称正则 */
    public static final String FILE_NAME_PATTERN = "[a-zA-Z0-9_/\\-\\.\\u4e00-\\u9fa5]+";

    /**
     * @description 构造器
     * @author zmzhou
     * @date 2020/8/30 21:21
     */
    private FileUtil() {
    }

    /**
     * 文件名称验证
     * @param filename 文件名称
     * @return false:正常     true:非法
     * @author zmzhou
     * @date 2020/8/30 21:18
     */
    public static boolean isValidFilename(String filename) {
        return !filename.matches(FILE_NAME_PATTERN);
    }
    /**
     * 输出指定文件的byte数组
     * @param filePath 文件路径
     * @param os       输出流
     * @author zmzhou
     * @date 2020/8/30 21:17
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        try (FileInputStream fis = new FileInputStream(file)){
            byte[] b = new byte[Constants.BUFFER_SIZE];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } finally {
            IOUtils.close(os);
        }
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @param immediate 是否立刻删除，否则只删除10分钟前的文件
     * @return 删除成功
     * @author zmzhou
     * @date 2020/8/30 21:18
     */
    public static boolean deleteFile(String filePath, boolean immediate) {
        // 当前时间减一小时
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            if (immediate || file.lastModified() < cal.getTime().getTime()) {
                flag = delete(file);
            }
        } else if (file.isDirectory()) {
            // 是文件夹，先删除里面的文件，再删除文件夹
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                for (File value : files) {
                    deleteFile(value.getAbsolutePath(), immediate);
                }
            }
            // 删除文件夹
            if (immediate || file.lastModified() < cal.getTime().getTime()) {
                flag = delete(file);
            }
        }
        return flag;
    }
    /**
     * 删除文件
     * @param file 文件
     * @return 删除成功结果
     * @author zmzhou
     * @date 2020/9/13 16:23
     */
    public static boolean delete(File file) {
        try {
            Files.delete(file.toPath());
            log.info("删除文件：{}", file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            log.error("删除文件[{}]失败", file.getAbsolutePath());
            return false;
        }
    }
    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        try {
            if (agent.contains("MSIE")) {
                // IE浏览器
                filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
                filename = filename.replace("+", " ");
            } else if (agent.contains("Firefox")) {
                // 火狐浏览器
                filename = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1.name());
            } else {
                // google浏览器或者其它浏览器
                filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
            }
        } catch (UnsupportedEncodingException e) {
            log.error("文件名重新编码异常", e);
            throw new BaseException(500, "文件名重新编码异常", fileName);
        }
        return filename;
    }

    /**
     * @description 判断文件夹路径是否存在，不存在则创建
     * @param path 文件夹路径
     * @author zmzhou
     * @date 2020/9/3 21:37
     */
    public static void existsAndMkdirs(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean flag = dir.mkdirs();
            log.info("创建文件夹：{} 成功：{}", path, flag);
        }
    }

    /**
     * @description 判断文件是否存在
     * @param filePath 文件路径
     * @return 文件是否存在
     * @author zmzhou
     * @date 2020/9/3 21:45
     */
    public static boolean exists(String filePath) {
        File dir = new File(filePath);
        return dir.exists();
    }
    /**
     * @description  根据contentType获取文件后缀
     * @param contentType contentType
     * @return  文件后缀
     * @author zmzhou
     * @date 2020/9/6 16:38
     */
    public static String getFileSuffix(String contentType){
        if (StringUtils.isBlank(contentType)){
            return "";
        }
        return Constants.DOT + contentType.substring(contentType.lastIndexOf(Constants.SEPARATOR) + 1);
    }
}
