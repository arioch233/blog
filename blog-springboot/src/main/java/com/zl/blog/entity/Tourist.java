package com.zl.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 游客信息实体
 *
 * @TableName tb_tourist
 */
@TableName(value = "tb_tourist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tourist implements Serializable {
    /**
     * 游客id
     */
    @TableId
    private Integer id;

    /**
     * 游客认证信息
     */
    private String touristAuth;

    /**
     * 游客昵称
     */
    private String touristNickname;

    /**
     * 游客头像
     */
    private String touristAvatar;

    /**
     * 游客邮箱
     */
    private String touristEmail;

    /**
     * 游客个人网站
     */
    private String touristWebsite;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}