package com.github.zmzhou.easyboot.common.utils;

import java.util.Collections;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.vo.IpInfo;

import lombok.extern.slf4j.Slf4j;


/**
 * The type Ip utils.
 *
 * @author zmzhou
 * @description 获取IP方法
 * @date 2020 /07/03 10:37
 */
@Slf4j
public final class IpUtils {
    /**
     * The constant 未知地址.
     */
    public static final String UNKNOWN = "unknown";
    /** 本地IP地址 */
    public static final String LOCALHOST = "127.0.0.1";
    /**
     * 私有构造器
     * @author zmzhou
     * @date 2020/08/29 14:18
     */
    private IpUtils() {
    }
    /**
     * 获取用户真实地址
     * @return 真实地址
     * @author zmzhou
     * @date 2020/9/12 15:40
     */
    public static String getRealAddress() {
        IpInfo info = getIpInfo();
        if (null != info) {
            return info.getAddr();
        }
        return UNKNOWN;
    }
    /**
     * 获取用户真实IP地址
     * @return 真实IP
     * @author zmzhou
     * @date 2020/9/12 15:39
     */
    public static String getRealIp() {
        IpInfo info = getIpInfo();
        if (null != info) {
            return info.getIp();
        }
        return UNKNOWN;
    }
    /**
     * 获取用户ip定位信息
     * @return IpInfo
     * @author zmzhou
     * @date 2020/9/12 15:34
     */
    public static IpInfo getIpInfo() {
        try {
            // 获取用户地理位置
            String response = HttpUtils.get(IpInfo.GET_IP_URL, Collections.singletonMap("json", "true"));
            if (StringUtils.isEmpty(response)) {
                log.error("获取地理位置异常");
                return null;
            }
            // 解析ip地址报文
            Matcher matcher = IpInfo.INFO_PATTERN.matcher(response);
            if (matcher.find()){
                IpInfo info = JSON.parseObject(matcher.group(1), IpInfo.class);
                log.info("用户ip信息：{}", info);
                return info;
            }
        } catch (Exception e) {
            log.error("获取地理位置异常", e);
        }
        return null;
    }

    /**
     * Gets ip addr.
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * @param request the request
     * @return the ip addr
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(Constants.COMMA)) {
                ip = ip.split(Constants.COMMA)[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String ipAddr = "0:0:0:0:0:0:0:1".equals(ip) ? LOCALHOST : ip;
        // 获取用户真实IP地址
        if (LOCALHOST.equals(ipAddr)) {
            return getRealIp();
        }
        return ipAddr;
    }

}
