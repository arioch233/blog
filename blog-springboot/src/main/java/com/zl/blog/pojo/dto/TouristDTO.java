package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游客信息
 *
 * @author 冷血毒舌
 * @create 2022/11/29 20:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouristDTO {
    /**
     * 游客id
     */
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
}
