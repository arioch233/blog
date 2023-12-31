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
    public static final String WEBSITE_NAME = "blog";

    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 验证码
     */
    public static final String USER_CODE_KEY = "code:";

    /**
     * 博客浏览量
     */
    public static final String BLOG_VIEWS_COUNT = "blog_views_count";

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEWS_COUNT = "blog_article_views_count";

    /**
     * 文章点赞量
     */
    public static final String ARTICLE_LIKE_COUNT = "blog_article_like_count";

    /**
     * 用户点赞文章
     */
    public static final String ARTICLE_USER_LIKE = "blog_article_user_like:";

    /**
     * 评论点赞量
     */
    public static final String COMMENT_LIKE_COUNT = "blog_comment_like_count";

    /**
     * 用户点赞评论
     */
    public static final String COMMENT_USER_LIKE = "blog_comment_user_like:";

    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";

    /**
     * 用户地区
     */
    public static final String USER_AREA = "user_area";

    /**
     * 访客地区
     */
    public static final String VISITOR_AREA = "blog_visitor_area";

    /**
     * 页面封面
     */
    public static final String PAGE_COVER = "page_cover";

    /**
     * 关于我信息
     */
    public static final String ABOUT = "about";

    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "blog_unique_visitor";

    /**
     * 用户token
     */
    public static final String USER_TOKEN = "blog_user_token";

    /**
     * 用户信息
     */
    public static final String USER_INFO = "blog_user_info";

    /**
     * 用户角色
     */
    public static final String USER_ROLE = "ROLE:userId=";
    /**
     * 用户角色权限
     */
    public static final String USER_PERMISSION = "PERMISSION:userId=";
    /**
     * 用户菜单
     */
    public static final String USER_MENU = "MENU:userId=";


}
