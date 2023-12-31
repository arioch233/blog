package com.zl.blog.utils;

import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;


/**
 * shiro 工具类
 * <p>
 * 用于快速获取登录信息
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:24
 */
public class ShiroUtils {
    /**
     * md5 盐
     */
    private static final String SALT = "xx.com";

    /**
     * 获取登录信息
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户id
     *
     * @param c
     * @param <T> id 类型
     * @return
     */
    public static <T> T getUserId(Class<T> c) {
        Subject subject = getSubject();
        Claims claims = (Claims) subject.getPrincipal();
        return claims.get("userId", c);
    }

    /**
     * 密码md5加密
     *
     * @param password 密码
     * @return
     */
    public static String md5(String password) {
        return new Md5Hash(password, SALT, 1024).toString();
    }

    /**
     * 密码比较
     *
     * @param password    未加密的密码
     * @param md5password 加密过的密码
     * @return
     */
    public static boolean verifyPassword(String password, String md5password) {
        return new Md5Hash(password, SALT, 1024).toString().equals(md5password);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        getSubject().logout();
    }

    public static void main(String[] args) {
        System.out.println(md5("123456"));
    }
}
