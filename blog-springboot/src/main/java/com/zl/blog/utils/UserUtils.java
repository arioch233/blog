package com.zl.blog.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户工具类
 *
 * @author 冷血毒舌
 * @create 2022/11/28 16:11
 */
public class UserUtils {
    /**
     * 生成用户登录信息md5
     *
     * @param request
     * @return
     */
    public static String generateUserMd5(HttpServletRequest request) {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        return DigestUtils.md5DigestAsHex(uuid.getBytes());
    }
}
