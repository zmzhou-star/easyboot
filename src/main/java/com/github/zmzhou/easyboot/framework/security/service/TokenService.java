package com.github.zmzhou.easyboot.framework.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.AmapUtils;
import com.github.zmzhou.easyboot.common.utils.IpUtils;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.vo.AmapAddressInfo;
import com.github.zmzhou.easyboot.framework.vo.IpInfo;

import cn.hutool.core.util.IdUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *  @title TokenService
 *  @Description token验证处理
 *  @author zmzhou
 *  @Date 2020/07/03 15:20
 */
@Component
public class TokenService {
    
    /**
     * The constant MILLIS_MINUTE. 一分钟
     */
    protected static final long MILLIS_MINUTE = 60 * 1000L;
    /** 20分钟 */
    private static final Long MILLIS_MINUTE_TEN = 20 * MILLIS_MINUTE;
    
    /** 令牌自定义标识 */
    @Value("${token.header}")
    private String header;
    /** 令牌秘钥 */
    @Value("${token.secret}")
    private String secret;
    /** 令牌有效期（默认30分钟） */
    @Value("${token.expireTime}")
    private int expireTime;
    /** Token前缀字符 */
    @Value("${token.prefix}")
    private String prefix;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取用户身份信息
     *
     * @param request the request
     * @return 用户信息 login user
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 更新失效时间
            claims.setExpiration(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE));
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            return redisUtils.get(userKey);
        }
        return null;
    }

    /**
     * 设置用户身份信息
     *
     * @param loginUser the login user
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public void setLoginUser(LoginUser loginUser) {
        if (null != loginUser && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     *
     * @param token the token
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisUtils.delete(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌 string
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtil.simpleUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Constants.LOGIN_USER_KEY, token);
        claims.put(Constants.JWT_AUTHORITIES, JSON.toJSONString(loginUser.getPermissions()));
        return prefix + Jwts.builder()
                // 放入用户名和用户ID
                .setId(loginUser.getUser().getId() + "")
                // 主题
                .setSubject(loginUser.getUser().getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("easyBoot")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser the login user
     * @author zmzhou
     * @date 2020/08/26 16:35
     */
    public void verifyToken(LoginUser loginUser) {
        long expire = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expire - currentTime <= MILLIS_MINUTE_TEN) {
            // 刷新令牌有效期
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     * @author zmzhou
     * @date 2020/08/26 16:35
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        // 修改在线状态
        loginUser.getUser().setOnline("1");
        redisUtils.set(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(SpringUtils.getRequest().getHeader("User-Agent"));
        // 获取用户ip定位信息
        IpInfo ipInfo = IpUtils.getIpInfo();
        loginUser.setIpInfo(ipInfo);
        // 更新用户登录时间和登录在线状态
        SysUser user = loginUser.getUser();
        if (null == user) {
            // 登录失败user为空
            user = new SysUser();
            loginUser.setUser(user);
        }
        user.setLoginDate(new Date());
        if (null != ipInfo) {
            // 判断用户网络环境是否变化
            if (Constants.ONE.equals(user.getOnline()) &&
                    StringUtils.isNotBlank(user.getLoginIp()) && !user.getLoginIp().equals(ipInfo.getIp())) {
                throw new BaseException(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "网络环境变化，请重新登录！");
            }
            loginUser.setIpAddr(ipInfo.getIp());
            loginUser.setLoginLocation(ipInfo.getAddr());
            // 更新redis缓存和内存中的用户登录信息
            user.setLoginAddr(ipInfo.getAddr());
            user.setLoginIp(ipInfo.getIp());
        } else {
            loginUser.setIpAddr(IpUtils.getIpAddr(SpringUtils.getRequest()));
        }
        // 获取用户高德地图ip定位信息
        AmapAddressInfo addressInfo = AmapUtils.getInstance().getAddressInfo();
        if (null != addressInfo) {
            // 设置所在城市中心经纬度坐标
            loginUser.setCoordinate(addressInfo.getCenterCoordinates());
        }
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }
    
    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名 username from token
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request HttpServletRequest
     * @return token
     * @author zmzhou
     * @date 2020/08/26 16:36
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(prefix)) {
            token = token.replace(prefix, "");
        }
        return token;
    }

    /**
     * tokenId
     * @param uuid uuid
     * @return  tokenId
     * @author zmzhou
     * @date 2020/9/10 20:13
     */
    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
