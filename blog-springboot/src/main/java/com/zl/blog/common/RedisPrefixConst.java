package com.zl.blog.common;

/**
 * redis常量
 *
 * @author 冷血毒舌
 * @create 2022/10/27 16:23
 */
public class RedisPrefixConst {

    /**
     * 网站名
     */
    public static final String WEBSITE_NAME = "mms";

    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 验证码
     */
    public static final String USER_CODE_KEY = "code:";

    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";
}
