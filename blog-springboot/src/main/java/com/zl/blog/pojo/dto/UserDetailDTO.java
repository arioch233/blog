package com.zl.blog.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 * @author 冷血毒舌
 * @create 2022/11/17 23:36
 */
@Data
@Builder
public class UserDetailDTO {
    /**
     * 用户账号id
     */
    private Integer id;

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 登录方式
     */
    private Integer loginType;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户角色
     */
    private List<String> roleList;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String intro;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 点赞文章集合
     */
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    private Set<Object> commentLikeSet;


    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
}
