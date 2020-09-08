package com.github.zmzhou.easyboot.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Http utils.
 *
 * @author zmzhou
 * @title HttpUtils
 * @Description 通用http发送方法
 * @Date 2020 /07/03 15:44
 */
public final class HttpUtils {
    /**
     * log
     */
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    
    /**
     * 私有构造器
     *
     * @author zmzhou
     * @date 2020 /08/29 14:18
     */
    private HttpUtils() {
    }
    
    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果 string
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, StandardCharsets.UTF_8.name());
    }
    
    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果 string
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), contentType))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                log.info("result - {}", result);
            }
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url={},param={}",url,param, e);
        }
        return result.toString();
    }
}
