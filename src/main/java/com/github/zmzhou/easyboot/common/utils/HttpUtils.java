package com.github.zmzhou.easyboot.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * The type Http utils.
 *
 * @author zmzhou
 * @title HttpUtils
 * @Description 通用http发送方法
 * @Date 2020 /07/03 15:44
 */
@Slf4j
public final class HttpUtils {
    /**
     * 私有构造器
     *
     * @author zmzhou
     * @date 2020 /08/29 14:18
     */
    private HttpUtils() {
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 返回值
     * @author zmzhou
     * @date 2020/9/12 14:24
     */
    public static String get(String url, Map<String, String> params) {
        RestTemplate rest = new RestTemplate();
        // url定义为http://USER-SERVICE/user?name={name)
        StringBuilder reqUrl = new StringBuilder(url);
        if (null == params) {
            params = new HashMap<>();
        } else {
            // 拼接查询参数
            reqUrl.append("?");
            params.keySet().forEach(key -> reqUrl.append(key).append("=")
                    .append("{").append(key).append("}").append("&"));
            // 删除最后一个&
            reqUrl.deleteCharAt(reqUrl.length() - 1);
        }
        ResponseEntity<String> response = rest.getForEntity(reqUrl.toString(), String.class, params);
        log.info("get请求url: {} ，response - {}", reqUrl.toString(), response);
        // 去除返回值的空格
        if (null != response.getBody()) {
            return response.getBody().trim();
        }
        return null;
    }
}
