package com.github.zmzhou.easyboot.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

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
        // 请求开始时间戳
        long start = System.currentTimeMillis();
        RestTemplate rest = getRestTemplate();
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
        // 请求耗时
        long cost = System.currentTimeMillis() - start;
        log.info("get请求耗时：{}ms，url: {} ，response - {}", cost, reqUrl.toString(), response);
        // 去除返回值的空格
        if (null != response.getBody()) {
            return response.getBody().trim();
        }
        return null;
    }

    /**
     * 发送get请求，解析成返回对象
     * @param url 请求地址
     * @param params 请求参数
     * @param clazz  泛型对象
     * @return 返回值对象
     * @author zmzhou
     * @date 2020/9/16 21:59
     */
    public static <T> T get(String url, Map<String, String> params, Class<T> clazz) {
        String res = get(url, params);
        if (StringUtils.isNotBlank(res)) {
            return JSON.parseObject(res, clazz);
        }
        return null;
    }

    /**
     * RestTemplate 支持HTTP、HTTPS
     * @return RestTemplate
     * @author zmzhou
     * @date 2020/12/20 17:30
     */
    private static RestTemplate getRestTemplate() {
        // 支持HTTP、HTTPS
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setValidateAfterInactivity(2000);
        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据(response)的时间，超时抛出read timeout
                .setSocketTimeout(65000)
                // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectTimeout(5000)
                // 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .setConnectionRequestTimeout(1000)
                .build();
        // Apache HttpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }
}
